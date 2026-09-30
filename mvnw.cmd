@echo off
rem ---------------------------------------------------------------------------
rem Maven Wrapper (varianta simplificata, pentru Windows)
rem
rem Descarca o singura data Maven 3.9.11 in %USERPROFILE%\.m2\wrapper\dists
rem si ruleaza comanda ceruta, de exemplu:   mvnw.cmd spring-boot:run
rem Daca schimbati versiunea, actualizati si .mvn\wrapper\maven-wrapper.properties
rem ---------------------------------------------------------------------------
setlocal

set "MAVEN_VERSION=3.9.11"
set "NAME=apache-maven-%MAVEN_VERSION%"
set "URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/%NAME%-bin.zip"
set "BASEDIR=%~dp0"
if "%BASEDIR:~-1%"=="\" set "BASEDIR=%BASEDIR:~0,-1%"
set "DIST=%USERPROFILE%\.m2\wrapper\dists\%NAME%"

if not exist "%DIST%\bin\mvn.cmd" (
  echo Descarc %NAME% din %URL% ...
  powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "$ErrorActionPreference = 'Stop';" ^
    "$tmp = Join-Path $env:TEMP ('mvnw-' + [guid]::NewGuid());" ^
    "New-Item -ItemType Directory -Path $tmp | Out-Null;" ^
    "$zip = Join-Path $tmp 'maven.zip';" ^
    "Invoke-WebRequest -UseBasicParsing -Uri '%URL%' -OutFile $zip;" ^
    "Expand-Archive -Path $zip -DestinationPath $tmp;" ^
    "New-Item -ItemType Directory -Force -Path (Split-Path '%DIST%') | Out-Null;" ^
    "Move-Item -Path (Join-Path $tmp '%NAME%') -Destination '%DIST%';" ^
    "Remove-Item -Recurse -Force $tmp"
  if errorlevel 1 (
    echo Descarcarea Maven a esuat.
    exit /b 1
  )
)

set "MAVEN_PROJECTBASEDIR=%BASEDIR%"
cd /d "%BASEDIR%"
"%DIST%\bin\mvn.cmd" %*
