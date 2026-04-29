#!/usr/bin/env python3
"""
Telegram bot for Fluent CI — trigger smoke tests and check status from Telegram.

Commands:
  /trigger  — trigger Firebase smoke tests on GitHub Actions
  /status   — show the latest GitHub Actions run status
  /help     — show available commands

Setup:
  1. Open Telegram, search @BotFather, send /newbot, follow prompts → copy the token
  2. Send any message to your new bot, then run:
       python3 scripts/telegram_bot.py --get-chat-id
     to find your TELEGRAM_CHAT_ID
  3. Set env vars and run the bot:
       export TELEGRAM_BOT_TOKEN=your_token
       export GITHUB_TOKEN=your_github_pat
       python3 scripts/telegram_bot.py

Required env vars:
  TELEGRAM_BOT_TOKEN  — from BotFather
  GITHUB_TOKEN        — GitHub PAT with repo scope
"""
import os, sys, time, requests

BOT_TOKEN    = os.environ.get('TELEGRAM_BOT_TOKEN', '')
GITHUB_TOKEN = os.environ.get('GITHUB_TOKEN', '')
REPO         = 'ashishjio1990-design/mcpfluent'
BASE_URL     = f'https://api.telegram.org/bot{BOT_TOKEN}'

# ── Telegram helpers ───────────────────────────────────────────────────────────

def get_updates(offset=None):
    params = {'timeout': 30, 'allowed_updates': ['message']}
    if offset:
        params['offset'] = offset
    r = requests.get(f'{BASE_URL}/getUpdates', params=params, timeout=35)
    return r.json().get('result', [])

def send(chat_id, text):
    requests.post(f'{BASE_URL}/sendMessage', json={
        'chat_id': chat_id, 'text': text, 'parse_mode': 'Markdown',
        'disable_web_page_preview': True
    })

# ── GitHub helpers ─────────────────────────────────────────────────────────────

def trigger_workflow():
    r = requests.post(
        f'https://api.github.com/repos/{REPO}/dispatches',
        headers={'Authorization': f'token {GITHUB_TOKEN}',
                 'Accept': 'application/vnd.github.v3+json'},
        json={'event_type': 'firebase-build'}
    )
    return r.status_code == 204

def latest_run():
    r = requests.get(
        f'https://api.github.com/repos/{REPO}/actions/runs?per_page=1',
        headers={'Authorization': f'token {GITHUB_TOKEN}',
                 'Accept': 'application/vnd.github.v3+json'}
    )
    runs = r.json().get('workflow_runs', [])
    return runs[0] if runs else None

# ── Command handlers ───────────────────────────────────────────────────────────

def handle_trigger(chat_id):
    send(chat_id, '⏳ Triggering Firebase smoke tests\\.\\.\\.')
    if trigger_workflow():
        send(chat_id,
             '✅ *Workflow triggered\\!*\n'
             f'[View run](https://github.com/{REPO}/actions)')
    else:
        send(chat_id, '❌ Failed to trigger workflow\\. Check GITHUB\\_TOKEN\\.')

def handle_status(chat_id):
    run = latest_run()
    if not run:
        send(chat_id, 'No runs found\\.')
        return
    conclusion = run.get('conclusion') or run.get('status', 'unknown')
    icon = {'success': '✅', 'failure': '❌', 'cancelled': '⚪',
            'in_progress': '🔄', 'queued': '⏳'}.get(conclusion, '❓')
    send(chat_id,
         f"{icon} *{run['name']}*\n"
         f"Status: `{conclusion}`\n"
         f"Run \\#{run['run_number']}\n"
         f"[View details]({run['html_url']})")

def handle_help(chat_id):
    send(chat_id,
         '*Fluent CI Bot*\n\n'
         '/trigger — Run smoke tests against latest Firebase build\n'
         '/status  — Show latest GitHub Actions run\n'
         '/help    — Show this message')

# ── Main loop ──────────────────────────────────────────────────────────────────

def main():
    if '--get-chat-id' in sys.argv:
        print('Waiting for a message... Send any message to your bot now.')
        updates = get_updates()
        for u in updates:
            msg = u.get('message', {})
            chat = msg.get('chat', {})
            print(f"Chat ID : {chat.get('id')}")
            print(f"Username: {chat.get('username', 'N/A')}")
            print(f"Name    : {chat.get('first_name', '')} {chat.get('last_name', '')}")
        return

    if not BOT_TOKEN:
        print('ERROR: TELEGRAM_BOT_TOKEN is not set', file=sys.stderr)
        sys.exit(1)
    if not GITHUB_TOKEN:
        print('ERROR: GITHUB_TOKEN is not set', file=sys.stderr)
        sys.exit(1)

    print(f'Bot started. Listening for commands on repo: {REPO}')
    offset = None
    while True:
        try:
            updates = get_updates(offset)
            for update in updates:
                offset = update['update_id'] + 1
                msg     = update.get('message', {})
                text    = msg.get('text', '').strip()
                chat_id = msg.get('chat', {}).get('id')
                if not chat_id or not text:
                    continue
                print(f'[{chat_id}] {text}')
                if text.startswith('/trigger'):
                    handle_trigger(chat_id)
                elif text.startswith('/status'):
                    handle_status(chat_id)
                elif text.startswith('/help') or text.startswith('/start'):
                    handle_help(chat_id)
                else:
                    send(chat_id, 'Unknown command\\. Send /help to see options\\.')
        except requests.exceptions.ConnectionError:
            print('Connection error, retrying in 5s...')
            time.sleep(5)
        except Exception as e:
            print(f'Error: {e}')
            time.sleep(5)

if __name__ == '__main__':
    main()
