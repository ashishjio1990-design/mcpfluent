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

# --- SVG pie chart -------------------------------------------------------
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
        slices = [
            (passed,  '#97cc64'),
            (failed,  '#fd5a3e'),
            (skipped, '#aaaaaa'),
        ]
        angle = 0
        for count, color in slices:
            if count == 0:
                continue
            span = (count / total) * 360
            svg.append(pie_path(cx, cy, r, angle, angle + span, color))
            angle += span
        svg.append(f'<circle cx="{cx}" cy="{cy}" r="{r*0.45:.0f}" fill="white"/>')
        svg.append(f'<text x="{cx}" y="{cy - 8}" text-anchor="middle" font-size="22" font-weight="bold" fill="#333">{total}</text>')
        svg.append(f'<text x="{cx}" y="{cy + 14}" text-anchor="middle" font-size="11" fill="#888">TOTAL</text>')
    svg.append('</svg>')
    return ''.join(svg)

# --- status badge --------------------------------------------------------
COLORS = {'passed': '#97cc64', 'failed': '#fd5a3e',
          'broken': '#fd5a3e', 'skipped': '#aaaaaa', 'unknown': '#aaaaaa'}

def badge(status):
    c = COLORS.get(status, '#aaa')
    return (f'<span style="background:{c};color:#fff;padding:2px 10px;'
            f'border-radius:12px;font-size:11px;font-weight:600;'
            f'letter-spacing:.5px">{status.upper()}</span>')

rows = ''.join(
    f'<tr><td style="width:40px;color:#999">{i}</td>'
    f'<td>{r["name"]}</td>'
    f'<td style="width:110px">{badge(r["status"])}</td>'
    f'<td style="width:80px;color:#888">{r["duration"]}</td></tr>'
    for i, r in enumerate(results, 1)
)

run_id = os.environ.get('GITHUB_RUN_NUMBER', 'local')
branch = os.environ.get('GITHUB_REF_NAME',   'local')
sha    = (os.environ.get('GITHUB_SHA', '') or '')[:7]
ts     = datetime.utcnow().strftime('%Y-%m-%d %H:%M UTC')

html = f"""<!DOCTYPE html>
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
    <tbody>{rows if rows else '<tr><td colspan="4" style="text-align:center;color:#aaa;padding:20px">No test results found</td></tr>'}</tbody>
  </table>
</body>
</html>"""

out = 'allure-summary.html'
with open(out, 'w') as f:
    f.write(html)
print(f"Generated: {out} ({passed} passed, {failed} failed, {skipped} skipped)")
