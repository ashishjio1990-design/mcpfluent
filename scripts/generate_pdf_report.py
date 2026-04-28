#!/usr/bin/env python3
import json, glob, os, sys, math
from datetime import datetime

results = []
for f in sorted(glob.glob('target/allure-results/*-result.json')):
    try:
        with open(f) as fp:
            data = json.load(fp)
        start = data.get('start', 0) or 0
        stop  = data.get('stop',  0) or 0
        results.append({
            'name':   data.get('name', os.path.basename(f)),
            'status': data.get('status', 'unknown'),
            'duration': f"{(stop - start) / 1000:.2f}s" if stop and start else '-',
        })
    except Exception as e:
        print(f"Skipping {f}: {e}", file=sys.stderr)

passed  = sum(1 for r in results if r['status'] == 'passed')
failed  = sum(1 for r in results if r['status'] in ('failed', 'broken'))
skipped = sum(1 for r in results if r['status'] == 'skipped')
total   = len(results)

COLORS = {'passed': '#97cc64', 'failed': '#fd5a3e',
          'broken': '#fd5a3e', 'skipped': '#aaaaaa', 'unknown': '#aaaaaa'}

run_id = os.environ.get('GITHUB_RUN_NUMBER', 'local')
branch = os.environ.get('GITHUB_REF_NAME',   'local')
sha    = (os.environ.get('GITHUB_SHA', '') or '')[:7]
ts     = datetime.utcnow().strftime('%Y-%m-%d %H:%M UTC')

# ── SVG donut chart (for PDF) ──────────────────────────────────────────────
def pie_path(cx, cy, r, start_deg, end_deg, color):
    if end_deg - start_deg >= 359.9:
        return f'<circle cx="{cx}" cy="{cy}" r="{r}" fill="{color}"/>'
    s = math.radians(start_deg - 90)
    e = math.radians(end_deg   - 90)
    x1, y1 = cx + r * math.cos(s), cy + r * math.sin(s)
    x2, y2 = cx + r * math.cos(e), cy + r * math.sin(e)
    large  = 1 if (end_deg - start_deg) > 180 else 0
    return (f'<path d="M{cx},{cy} L{x1:.2f},{y1:.2f} '
            f'A{r},{r} 0 {large},1 {x2:.2f},{y2:.2f} Z" fill="{color}"/>')

def build_pie():
    cx, cy, r = 110, 110, 90
    svg = [f'<svg width="220" height="220" xmlns="http://www.w3.org/2000/svg">']
    if total == 0:
        svg.append(f'<circle cx="{cx}" cy="{cy}" r="{r}" fill="#eee"/>')
        svg.append(f'<text x="{cx}" y="{cy+5}" text-anchor="middle" font-size="13" fill="#999">No data</text>')
    else:
        angle = 0
        for count, color in [(passed,'#97cc64'),(failed,'#fd5a3e'),(skipped,'#aaaaaa')]:
            if count == 0:
                continue
            span = (count / total) * 360
            svg.append(pie_path(cx, cy, r, angle, angle + span, color))
            angle += span
        svg.append(f'<circle cx="{cx}" cy="{cy}" r="{r*0.45:.0f}" fill="white"/>')
        svg.append(f'<text x="{cx}" y="{cy-8}" text-anchor="middle" font-size="22" font-weight="bold" fill="#333">{total}</text>')
        svg.append(f'<text x="{cx}" y="{cy+14}" text-anchor="middle" font-size="11" fill="#888">TOTAL</text>')
    svg.append('</svg>')
    return ''.join(svg)

def pdf_badge(status):
    c = COLORS.get(status, '#aaa')
    return (f'<span style="background:{c};color:#fff;padding:2px 10px;'
            f'border-radius:12px;font-size:11px;font-weight:600;'
            f'letter-spacing:.5px">{status.upper()}</span>')

pdf_rows = ''.join(
    f'<tr><td style="width:40px;color:#999">{i}</td>'
    f'<td>{r["name"]}</td>'
    f'<td style="width:110px">{pdf_badge(r["status"])}</td>'
    f'<td style="width:80px;color:#888">{r["duration"]}</td></tr>'
    for i, r in enumerate(results, 1)
)

pdf_html = f"""<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
  * {{ box-sizing: border-box; margin: 0; padding: 0; }}
  body {{ font-family: Arial, sans-serif; color: #333; padding: 40px; background: #fff; }}
  h1   {{ font-size: 22px; color: #222; margin-bottom: 4px; }}
  .meta {{ font-size: 12px; color: #888; margin-bottom: 28px; }}
  .summary {{ display: flex; align-items: center; gap: 36px; margin-bottom: 32px;
               background: #fafafa; border: 1px solid #eee; border-radius: 8px; padding: 20px; }}
  .stats   {{ display: flex; gap: 28px; }}
  .stat    {{ text-align: center; }}
  .stat .num  {{ font-size: 36px; font-weight: 700; line-height: 1; }}
  .stat .lbl  {{ font-size: 11px; color: #888; margin-top: 4px; text-transform: uppercase; letter-spacing: .5px; }}
  .pass {{ color: #97cc64; }} .fail {{ color: #fd5a3e; }} .skip {{ color: #aaa; }} .tot {{ color: #333; }}
  table  {{ width: 100%; border-collapse: collapse; font-size: 13px; }}
  thead th {{ background: #f0f0f0; padding: 10px 12px; text-align: left;
              border-bottom: 2px solid #ddd; font-size: 12px; text-transform: uppercase;
              letter-spacing: .5px; color: #555; }}
  tbody td {{ padding: 9px 12px; border-bottom: 1px solid #f0f0f0; vertical-align: middle; }}
  tbody tr:last-child td {{ border-bottom: none; }}
</style>
</head>
<body>
  <h1>Regression Test Report</h1>
  <div class="meta">Run #{run_id} &nbsp;·&nbsp; Branch: {branch} &nbsp;·&nbsp; Commit: {sha} &nbsp;·&nbsp; {ts}</div>
  <div class="summary">
    {build_pie()}
    <div class="stats">
      <div class="stat"><div class="num pass">{passed}</div><div class="lbl">Passed</div></div>
      <div class="stat"><div class="num fail">{failed}</div><div class="lbl">Failed</div></div>
      <div class="stat"><div class="num skip">{skipped}</div><div class="lbl">Skipped</div></div>
      <div class="stat"><div class="num tot">{total}</div><div class="lbl">Total</div></div>
    </div>
  </div>
  <table>
    <thead><tr><th>#</th><th>Test Case</th><th>Status</th><th>Duration</th></tr></thead>
    <tbody>{pdf_rows if pdf_rows else '<tr><td colspan="4" style="text-align:center;color:#aaa;padding:20px">No test results found</td></tr>'}</tbody>
  </table>
</body>
</html>"""

# ── Email-safe HTML (table layout, all inline styles, no SVG) ──────────────
def pct(n):
    return f"{round((n / total) * 100) if total else 0}%"

def stat_box(num, label, bg):
    return (
        f'<td width="25%" style="background:{bg};text-align:center;'
        f'padding:18px 10px;border-radius:6px;">'
        f'<div style="font-size:38px;font-weight:700;color:#fff;line-height:1">{num}</div>'
        f'<div style="font-size:11px;color:rgba(255,255,255,.85);margin-top:5px;'
        f'text-transform:uppercase;letter-spacing:1px">{label}</div>'
        f'</td>'
    )

def email_badge(status):
    c = COLORS.get(status, '#aaa')
    return (f'<span style="background:{c};color:#fff;padding:3px 12px;'
            f'border-radius:12px;font-size:11px;font-weight:700;'
            f'letter-spacing:.5px;font-family:Arial,sans-serif">{status.upper()}</span>')

email_rows = ''.join(
    f'<tr style="background:{"#fafafa" if i % 2 == 0 else "#fff"}">'
    f'<td style="padding:10px 14px;color:#aaa;font-size:12px;width:40px;font-family:Arial,sans-serif">{i}</td>'
    f'<td style="padding:10px 14px;font-size:13px;font-family:Arial,sans-serif;color:#333">{r["name"]}</td>'
    f'<td style="padding:10px 14px;width:110px">{email_badge(r["status"])}</td>'
    f'<td style="padding:10px 14px;font-size:12px;color:#888;width:80px;font-family:Arial,sans-serif">{r["duration"]}</td>'
    f'</tr>'
    for i, r in enumerate(results, 1)
)

no_results_row = (
    '<tr><td colspan="4" style="text-align:center;color:#aaa;padding:24px;'
    'font-family:Arial,sans-serif;font-size:13px">No test results found</td></tr>'
)

p_pct = round((passed  / total * 100)) if total else 0
f_pct = round((failed  / total * 100)) if total else 0
s_pct = 100 - p_pct - f_pct if total else 0

email_html = f"""<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"></head>
<body style="margin:0;padding:0;background:#f4f4f4;font-family:Arial,sans-serif">
<table width="100%" cellpadding="0" cellspacing="0" style="background:#f4f4f4;padding:30px 0">
<tr><td align="center">
<table width="620" cellpadding="0" cellspacing="0" style="background:#fff;border-radius:8px;overflow:hidden;box-shadow:0 2px 8px rgba(0,0,0,.08)">

  <!-- Header -->
  <tr>
    <td style="background:#2d3748;padding:24px 28px">
      <div style="font-size:20px;font-weight:700;color:#fff">Regression Test Report</div>
      <div style="font-size:12px;color:#a0aec0;margin-top:6px">
        Run #{run_id} &nbsp;·&nbsp; Branch: {branch} &nbsp;·&nbsp; Commit: {sha} &nbsp;·&nbsp; {ts}
      </div>
    </td>
  </tr>

  <!-- Stat boxes -->
  <tr>
    <td style="padding:20px 28px 10px">
      <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
          {stat_box(passed,  'Passed',  '#97cc64')}
          <td width="8"></td>
          {stat_box(failed,  'Failed',  '#fd5a3e')}
          <td width="8"></td>
          {stat_box(skipped, 'Skipped', '#a0aec0')}
          <td width="8"></td>
          {stat_box(total,   'Total',   '#4a5568')}
        </tr>
      </table>
    </td>
  </tr>

  <!-- Proportional bar -->
  <tr>
    <td style="padding:14px 28px 20px">
      <table width="100%" cellpadding="0" cellspacing="0" style="border-radius:4px;overflow:hidden;height:10px">
        <tr>
          {'<td style="background:#97cc64;height:10px" width="' + str(p_pct) + '%"></td>' if p_pct else ''}
          {'<td style="background:#fd5a3e;height:10px" width="' + str(f_pct) + '%"></td>' if f_pct else ''}
          {'<td style="background:#a0aec0;height:10px" width="' + str(s_pct) + '%"></td>' if s_pct else ''}
          {'<td style="background:#eee;height:10px"></td>' if total == 0 else ''}
        </tr>
      </table>
      <table width="100%" cellpadding="0" cellspacing="0" style="margin-top:7px">
        <tr>
          <td style="font-size:11px;color:#97cc64;font-weight:600">&#9632; {p_pct}% Passed</td>
          <td style="font-size:11px;color:#fd5a3e;font-weight:600;text-align:center">&#9632; {f_pct}% Failed</td>
          <td style="font-size:11px;color:#a0aec0;font-weight:600;text-align:right">&#9632; {s_pct}% Skipped</td>
        </tr>
      </table>
    </td>
  </tr>

  <!-- Section label -->
  <tr>
    <td style="padding:0 28px 10px">
      <div style="font-size:12px;font-weight:700;color:#718096;text-transform:uppercase;letter-spacing:1px;border-bottom:2px solid #edf2f7;padding-bottom:8px">
        Test Results
      </div>
    </td>
  </tr>

  <!-- Test table -->
  <tr>
    <td style="padding:0 28px 24px">
      <table width="100%" cellpadding="0" cellspacing="0" style="font-size:13px;border-collapse:collapse">
        <thead>
          <tr style="background:#edf2f7">
            <th style="padding:10px 14px;text-align:left;font-size:11px;text-transform:uppercase;letter-spacing:.5px;color:#718096;font-weight:700;width:40px">#</th>
            <th style="padding:10px 14px;text-align:left;font-size:11px;text-transform:uppercase;letter-spacing:.5px;color:#718096;font-weight:700">Test Case</th>
            <th style="padding:10px 14px;text-align:left;font-size:11px;text-transform:uppercase;letter-spacing:.5px;color:#718096;font-weight:700;width:110px">Status</th>
            <th style="padding:10px 14px;text-align:left;font-size:11px;text-transform:uppercase;letter-spacing:.5px;color:#718096;font-weight:700;width:80px">Duration</th>
          </tr>
        </thead>
        <tbody>
          {email_rows if email_rows else no_results_row}
        </tbody>
      </table>
    </td>
  </tr>

  <!-- Footer -->
  <tr>
    <td style="background:#f7fafc;padding:14px 28px;border-top:1px solid #edf2f7">
      <div style="font-size:11px;color:#a0aec0;text-align:center">
        Fluent Health &nbsp;·&nbsp; Automated Regression Suite &nbsp;·&nbsp; {ts}
      </div>
    </td>
  </tr>

</table>
</td></tr>
</table>
</body>
</html>"""

with open('allure-summary.html', 'w') as f:
    f.write(pdf_html)

with open('allure-email.html', 'w') as f:
    f.write(email_html)

# Write EMAIL_HTML to $GITHUB_ENV using a random delimiter so the bash
# heredoc approach (which breaks when the file has no trailing newline) is
# never needed in the workflow.
github_env = os.environ.get('GITHUB_ENV', '')
if github_env:
    import random, string
    delim = 'EMAILDELIM_' + ''.join(random.choices(string.ascii_uppercase + string.digits, k=12))
    with open(github_env, 'a') as env_file:
        env_file.write(f'EMAIL_HTML<<{delim}\n{email_html}\n{delim}\n')
    print("EMAIL_HTML written to GITHUB_ENV")

print(f"Generated PDF HTML and email HTML ({passed} passed, {failed} failed, {skipped} skipped)")
