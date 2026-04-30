#!/usr/bin/env python3
"""Delete Allure result files for specified classes so phase-2 rerun results are not double-counted."""
import glob
import json
import os
import sys

if len(sys.argv) < 2 or not sys.argv[1].strip():
    print("No classes provided — nothing to clean.")
    sys.exit(0)

failed_classes = [c.strip() for c in sys.argv[1].split("+") if c.strip()]
results_dir = sys.argv[2] if len(sys.argv) > 2 else "target/allure-results"

removed = 0
for json_file in glob.glob(f"{results_dir}/*-result.json"):
    try:
        with open(json_file, encoding="utf-8") as f:
            data = json.load(f)
        full_name = data.get("fullName", "")
        # fullName format: com.fluent.tests.SignInTest#methodName
        class_name = full_name.split("#")[0]
        if class_name in failed_classes:
            os.remove(json_file)
            removed += 1
            print(f"  Removed: {os.path.basename(json_file)} ({full_name})")
    except Exception as e:
        print(f"  Warning: could not process {json_file}: {e}", file=sys.stderr)

print(f"Cleaned {removed} allure result(s) for rerun.")
