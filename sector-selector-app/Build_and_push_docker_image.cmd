@ECHO OFF
docker build -t collos/sector-selector-app .
docker push collos/sector-selector-ui
IF ERRORLEVEL 1 PAUSE