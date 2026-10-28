# Run from the DevCheck-CLI project root in PowerShell.
$ErrorActionPreference = "Stop"
Write-Host "Checking Java and Python..."
java -version
javac -version
python --version
Write-Host "`nInstalling optional Python quality tools..."
python -m pip install -r python/requirements-dev.txt
Write-Host "`nRunning Python unit tests..."
python -m unittest discover -s python/tests -v
Write-Host "`nCompiling Java sources..."
New-Item -ItemType Directory -Force -Path java/build | Out-Null
$javaFiles = Get-ChildItem -Path java/src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d java/build $javaFiles
Write-Host "`nSetup checks complete. For the Bash launcher and Java GUI, use Git Bash: ./bin/devcheck gui"
