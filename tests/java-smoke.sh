#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
"$ROOT/bin/devcheck" java calc add 2 3 | grep -q '5.0'
"$ROOT/bin/devcheck" java palindrome level | grep -q 'Yes'
"$ROOT/bin/devcheck" java greet Sandesh | grep -q 'Hello, Sandesh!'
echo "Java smoke tests passed (3 checks)."
