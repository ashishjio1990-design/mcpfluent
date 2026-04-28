#!/usr/bin/env python3
"""Download the latest APK from Firebase App Distribution.

Usage: python3 scripts/download_firebase_apk.py <output_path>

Required env vars:
  FIREBASE_SERVICE_ACCOUNT_JSON  – service account JSON (string)
  FIREBASE_APP_ID                – e.g. 1:123456789:android:abcdef
"""
import json, os, sys, re
import requests
from google.oauth2 import service_account
import google.auth.transport.requests

sa_json_str = os.environ.get('FIREBASE_SERVICE_ACCOUNT_JSON', '')
app_id       = os.environ.get('FIREBASE_APP_ID', '')
output_path  = sys.argv[1] if len(sys.argv) > 1 else 'apps/firebase-latest.apk'

if not sa_json_str or not app_id:
    print("ERROR: FIREBASE_SERVICE_ACCOUNT_JSON and FIREBASE_APP_ID must be set", file=sys.stderr)
    sys.exit(1)

# Extract project number from app_id format: 1:PROJECT_NUMBER:android:HASH
m = re.match(r'1:(\d+):android:', app_id)
if not m:
    print(f"ERROR: Cannot parse project number from FIREBASE_APP_ID: {app_id}", file=sys.stderr)
    sys.exit(1)
project_number = m.group(1)

# Authenticate with service account
credentials = service_account.Credentials.from_service_account_info(
    json.loads(sa_json_str),
    scopes=['https://www.googleapis.com/auth/cloud-platform']
)
credentials.refresh(google.auth.transport.requests.Request())
token = credentials.token

headers = {'Authorization': f'Bearer {token}'}

# Fetch the latest release
url = (f'https://firebaseappdistribution.googleapis.com/v1'
       f'/projects/{project_number}/apps/{app_id}/releases'
       f'?orderBy=createTime+desc&pageSize=1')
resp = requests.get(url, headers=headers)
resp.raise_for_status()

releases = resp.json().get('releases', [])
if not releases:
    print("ERROR: No releases found in Firebase App Distribution", file=sys.stderr)
    sys.exit(1)

release = releases[0]
version  = release.get('displayVersion', 'unknown')
build    = release.get('buildVersion',   'unknown')
print(f"Latest release: v{version} (build {build}) — {release.get('createTime', '')}")

binary_uri = release.get('binaryDownloadUri')
if not binary_uri:
    print("ERROR: binaryDownloadUri missing from release response", file=sys.stderr)
    sys.exit(1)

os.makedirs(os.path.dirname(output_path) or '.', exist_ok=True)
print(f"Downloading APK to {output_path} ...")
with requests.get(binary_uri, stream=True) as r:
    r.raise_for_status()
    with open(output_path, 'wb') as f:
        for chunk in r.iter_content(chunk_size=65536):
            f.write(chunk)

size_mb = os.path.getsize(output_path) / 1024 / 1024
print(f"Done — {size_mb:.2f} MB saved to {output_path}")
print(f"::set-output name=apk_version::{version}")
print(f"::set-output name=apk_build::{build}")
