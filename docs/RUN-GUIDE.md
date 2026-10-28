# DevCheck CLI — Command Run Guide

Use **Git Bash** (not PowerShell) for the `./bin/devcheck` commands.

## A. Open the project folder

```bash
cd /d/ITM\ PYTHON/Linux/Final_CLI_linux_project/DevCheck-CLI
```

If the folder is somewhere else, use its actual Git Bash path.

## B. Check prerequisites

```bash
python --version
java -version
javac -version
```

Python must be 3.10 or newer. Java and `javac` are needed for Java commands and the Java smoke test.

## C. Install the Python package in editable mode

```bash
python -m pip install -e ./python
```

This makes the Python package importable in the current Python environment. It is a one-time setup per environment.

## D. Run Python unit tests

```bash
python -m unittest discover -s python/tests -v
```

Run this before demoing. Fix any failures before continuing.

## E. Run Java smoke tests

```bash
bash tests/java-smoke.sh
```

The script invokes the project launcher, which compiles/runs the Java source. Expected final line: `Java smoke tests passed (3 checks).`

## F. Try basic commands

Run one by one:

```bash
./bin/devcheck py calc add 5 3
./bin/devcheck py stats 10 20 30
./bin/devcheck py reverse hello world
./bin/devcheck py palindrome "Never odd or even"
./bin/devcheck py fileinfo README.md
./bin/devcheck py hash README.md
./bin/devcheck py tree .
```

## G. Run the CLOC-like command

```bash
./bin/devcheck py cloc .
```

This reports supported language groups with file, blank, comment, and code counts. Use a project path argument to scan a different folder:

```bash
./bin/devcheck py cloc ./python
```

## H. Run mathx

```bash
./bin/devcheck py mathx mod 10 3
./bin/devcheck py mathx factorial 5
./bin/devcheck py mathx integrate-x2 0 3
./bin/devcheck py mathx vector-add 1,2,3 4,5,6
./bin/devcheck py mathx vector-dot 1,2,3 4,5,6
./bin/devcheck py mathx ml-predict 1,2,3 2,4,6 4
```

For vector inputs, comma-separated numbers are one argument; do not add spaces inside a vector.

## I. Run Java commands

```bash
./bin/devcheck java greet Sandesh
./bin/devcheck java calc add 2 3
./bin/devcheck java reverse hello
./bin/devcheck java stats 10 20 30
./bin/devcheck java mathx factorial 5
./bin/devcheck java cloc .
```

## J. Capture evidence for your report

Copy the actual terminal output for:

```bash
python --version
java -version
javac -version
python -m unittest discover -s python/tests -v
bash tests/java-smoke.sh
./bin/devcheck py cloc .
```

Do not put fabricated CLOC counts or test results in the report.

## K. GitHub submission

After replacing the README placeholder with your own repository URL:

```bash
git status
git add README.md REPORT-TEMPLATE.md docs
git commit -m "docs: add project report and command run guide"
git push
```

If the project was not cloned from your own GitHub repo yet, create the repo and follow the GitHub instructions to add the remote first. Do not run `git push` until `git remote -v` points to your repository.
