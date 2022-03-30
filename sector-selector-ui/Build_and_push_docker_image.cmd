@ECHO OFF
docker build -t collos/sector-selector-ui .
docker push collos/sector-selector-ui
IF ERRORLEVEL 1 PAUSE
