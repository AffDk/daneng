@echo off
REM Convenience script to run Gradle commands from the root directory
cd /d "%~dp0android"
call gradlew.bat %*
