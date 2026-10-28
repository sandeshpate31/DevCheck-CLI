# DevCheck CLI — Command Reference

## General syntax

```text
devcheck <py|java> <command> [arguments]
```

From the repository root in Git Bash, call the launcher as `./bin/devcheck`.

## Python

| Command | Syntax | Example |
|---|---|---|
| calc | `calc <add\|subtract\|multiply\|divide\|power> <a> <b>` | `./bin/devcheck py calc add 5 3` |
| stats | `stats <number>...` | `./bin/devcheck py stats 10 20 30` |
| reverse | `reverse <text>...` | `./bin/devcheck py reverse hello world` |
| palindrome | `palindrome <text>...` | `./bin/devcheck py palindrome level` |
| count | `count <text>...` | `./bin/devcheck py count hello world` |
| upper | `upper <text>...` | `./bin/devcheck py upper hello` |
| lower | `lower <text>...` | `./bin/devcheck py lower HELLO` |
| fileinfo | `fileinfo <path>` | `./bin/devcheck py fileinfo README.md` |
| tree | `tree [path]` | `./bin/devcheck py tree .` |
| hash | `hash <path>` | `./bin/devcheck py hash README.md` |
| cloc | `cloc [path]` | `./bin/devcheck py cloc .` |
| mathx | `mathx <operation> <values...>` | `./bin/devcheck py mathx factorial 5` |

### Python mathx operations

```bash
./bin/devcheck py mathx mod 10 3
./bin/devcheck py mathx factorial 5
./bin/devcheck py mathx integrate-x2 0 3
./bin/devcheck py mathx vector-add 1,2,3 4,5,6
./bin/devcheck py mathx vector-dot 1,2,3 4,5,6
./bin/devcheck py mathx ml-predict 1,2,3 2,4,6 4
```

## Java

| Command | Syntax | Example |
|---|---|---|
| greet | `greet <name>` | `./bin/devcheck java greet Sandesh` |
| calc | `calc <add\|subtract\|multiply\|divide> <a> <b>` | `./bin/devcheck java calc add 2 3` |
| reverse | `reverse <text>...` | `./bin/devcheck java reverse hello` |
| palindrome | `palindrome <text>...` | `./bin/devcheck java palindrome level` |
| count | `count <text>...` | `./bin/devcheck java count hello world` |
| upper/lower | `upper <text>...` | `./bin/devcheck java upper hello` |
| stats | `stats <number>...` | `./bin/devcheck java stats 10 20 30` |
| fileinfo | `fileinfo <path>` | `./bin/devcheck java fileinfo README.md` |
| mathx | `mathx <operation> <values...>` | `./bin/devcheck java mathx factorial 5` |
| cloc | `cloc [path]` | `./bin/devcheck java cloc .` |
| help | `help` | `./bin/devcheck java help` |

## GUI COMMAND 
sande@sandesh MINGW64 /d/ITM PYTHON/Linux/Final_CLI_linux_project/DevCheck-CLI
1) $  |` java -cp java/build devcheck.DevCheckGui`|

2) |`java -cp java/build devcheck.DevCheckGui1`|



## Important behavior

- `fileinfo`, `tree`, `hash`, and `cloc` operate on local paths.
- The CLOC-like scanner is an educational approximation, not a full language parser.
- `cloc` skips `.git`, virtual environments, dependency folders, and common build output folders.
- Current commands do not require network access or AI API keys.
- Exact error output and exit codes should be verified by running commands on the submission machine.
