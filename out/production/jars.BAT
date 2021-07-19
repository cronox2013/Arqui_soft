cd MonitorCovid
RMDIR /Q/S ..\jars\
jar -cvf cargarsintomas.jar cargarsintomas/*
jar -cvf cargarregistros.jar cargarregistros/*
jar -cvf home.jar Main.class monitor/* diagnosticos/* sintomas
mkdir ..\jars
move .\cargarregistros.jar ..\jars\
move .\cargarsintomas.jar ..\jars\   
move .\home.jar ..\jars\
cd ..\jars\
md resources
copy ..\..\..\..\AndyGarciaSintomas.dat AndyGarciaSintomas.dat
java -cp "home.jar;cargarsintomas.jar;cargarregistros.jar" Main