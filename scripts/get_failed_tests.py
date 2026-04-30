#!/usr/bin/env python3
"""Parse Surefire XML reports and print '+'-separated failed class names for Maven -Dtest=."""
import glob
import sys
import xml.etree.ElementTree as ET

reports_dir = sys.argv[1] if len(sys.argv) > 1 else "target/surefire-reports"
failed = set()

for xml_file in glob.glob(f"{reports_dir}/TEST-*.xml"):
    try:
        root = ET.parse(xml_file).getroot()
        class_name = root.get("name", "")
        for tc in root.findall("testcase"):
            if tc.find("failure") is not None or tc.find("error") is not None:
                failed.add(class_name)
                break
    except Exception as e:
        print(f"Warning: could not parse {xml_file}: {e}", file=sys.stderr)

print("+".join(sorted(failed)))
