@Echo OFF
Pushd "%~dp0"
CD ../
SET IP=127.0.0.1
SET APP=simtopup-user-0.0.1.jar
SET JVM=-server -d64 -Xms128m -Xmx2048m 
SET JMX=-Djava.rmi.server.hostname=%IP% -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=2010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false 
SET LOG=-Dlogback.configurationFile=config/logback.xml 
cmd /k java %JVM% %JMX% %LOG% -Djava.net.preferIPv4Stack=true -jar %APP% 

