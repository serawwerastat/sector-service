@ECHO OFF
docker build -t collos/sector-selector-app .
docker push collos/sector-selector-app
IF ERRORLEVEL 1 PAUSE