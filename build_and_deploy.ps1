# ============================================================
#  ATcraft-Addon-164 一键编译 + 部署脚本
#  用法: .\build_and_deploy.ps1
# ============================================================

$ErrorActionPreference = "Stop"

$JAVA_HOME     = "F:\jdk8\jdk8u492-b09"
$PROJECT_DIR   = "F:\ATcraft-Addon-164\forge"
$SRG_JAR       = "$PROJECT_DIR\build\libs\ATcraft-Addon-164-1.0-srg.jar"
$SRC_JAR       = "$PROJECT_DIR\build\libs\ATcraft-Addon-164-1.0.jar"
$SPECIALSOURCE = "$PROJECT_DIR\forge-src\forge\mcp\runtime\bin\specialsource.jar"
$MAPPING       = "$PROJECT_DIR\forge-src\forge\mcp\conf\mcp2srg.srg"
$DEPLOY        = "C:\Users\Yuanqiubai\Desktop\冒险者传说\☆冒险者传说V1.9071测试版(1)\.minecraft\mods\ATcraft-Addon-164-1.0-srg.jar"

Write-Host "==== ATcraft-Addon-164 Build ====" -ForegroundColor Cyan

# [1/3] Compile
Write-Host "[1/3] Gradle build..." -ForegroundColor Yellow
$env:JAVA_HOME = $JAVA_HOME
Push-Location $PROJECT_DIR
try {
    & .\gradlew.bat build --no-daemon 2>&1
    if ($LASTEXITCODE -ne 0) { throw "Gradle build failed" }
    Write-Host "  BUILD SUCCESSFUL" -ForegroundColor Green
} finally { Pop-Location }

# [2/3] SRG remap
Write-Host "[2/3] SpecialSource remap..." -ForegroundColor Yellow
& "$JAVA_HOME\bin\java.exe" -jar $SPECIALSOURCE -i $SRC_JAR -o $SRG_JAR -m $MAPPING -r 2>&1
if ($LASTEXITCODE -ne 0) { throw "SpecialSource failed" }
Write-Host "  Remapping done" -ForegroundColor Green

# [3/3] Deploy
Write-Host "[3/3] Deploy + MD5..." -ForegroundColor Yellow
Copy-Item $SRG_JAR $DEPLOY -Force
$h1 = (Get-FileHash $SRG_JAR -Algorithm MD5).Hash
$h2 = (Get-FileHash $DEPLOY -Algorithm MD5).Hash
if ($h1 -eq $h2) { Write-Host "  OK  MD5: $h1" -ForegroundColor Green }
else { throw "MD5 mismatch!" }

Write-Host "==== DONE ====" -ForegroundColor Cyan
