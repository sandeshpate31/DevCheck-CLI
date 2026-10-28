# Enhanced starter — changes

## Retained from uploaded starter
- Python command dispatcher and 10 Python commands.
- Java command dispatcher and Java MathKit/TextKit reusable classes.
- Python arithmetic, text, and read-only file helper libraries.
- Python unit tests, Java smoke test, launcher, report template, and research notes.

## Added
- Java Swing GUI (`java/src/devcheck/DevCheckGui.java`) launched with `./bin/devcheck gui`.
- Flake8 configuration (`.flake8`) and optional development dependencies (`python/requirements-dev.txt`).
- Makefile targets for tests, lint, Radon complexity/maintainability, CLOC, and Java smoke tests.
- Command reference (`docs/COMMANDS.md`) and Windows setup/check script (`setup-windows.ps1`).
- Expanded README, generated-file ignore rules, and launcher help for the GUI.

## Validation performed in this build environment
- Python tests: 5 passed.
- Java compilation: passed.
- Java smoke checks: 3 passed.
- Python `stats` command and Java `palindrome` command: passed.

Flake8, Radon, CLOC, and graphical window interaction were not reported as tested here. Run those locally and paste the actual output into the report.
