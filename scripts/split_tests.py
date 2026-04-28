#!/usr/bin/env python3
"""
Discover test classes that contain a given @Tag and split them across N shards.
Usage: python3 split_tests.py <tag> <n_shards>
Output: matrix={"include":[{"shard":1,"tests":"ClassA,ClassB"},…]}  → GITHUB_OUTPUT
"""
import glob, json, os, sys

tag      = sys.argv[1] if len(sys.argv) > 1 else 'regression'
n_shards = int(sys.argv[2]) if len(sys.argv) > 2 else 2

# Collect test classes that contain @Tag("<tag>")
tagged = []
for path in sorted(glob.glob('src/test/java/**/*Test.java', recursive=True)):
    with open(path) as f:
        if f'@Tag("{tag}")' in f.read():
            tagged.append(os.path.basename(path).replace('.java', ''))

if not tagged:
    print(f'No classes found with @Tag("{tag}"), falling back to all test classes.', file=sys.stderr)
    tagged = [os.path.basename(p).replace('.java', '')
              for p in sorted(glob.glob('src/test/java/**/*Test.java', recursive=True))]

# Cap shards at number of available classes
n_shards = min(n_shards, len(tagged))

# Round-robin split for even distribution
shards = [[] for _ in range(n_shards)]
for i, cls in enumerate(tagged):
    shards[i % n_shards].append(cls)

matrix = {
    'include': [
        {'shard': i + 1, 'tests': ','.join(s)}
        for i, s in enumerate(shards) if s
    ]
}

result = f"matrix={json.dumps(matrix)}"
print(result)
print(f"Split {len(tagged)} class(es) across {n_shards} shard(s): {[s['tests'] for s in matrix['include']]}", file=sys.stderr)

if os.environ.get('GITHUB_OUTPUT'):
    with open(os.environ['GITHUB_OUTPUT'], 'a') as f:
        f.write(result + '\n')
