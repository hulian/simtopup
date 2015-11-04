#!/bin/bash


export JAVA_HOME=/home/simtopup/jdk1.8.0_66
export PATH=$PATH:/home/simtopup/jdk1.8.0_66/bin

cd ../
find . -type f -name '*.gzip' -delete


IP=103.224.249.113
APP="simtopup-user-0.0.1.jar"
JVM=" -server -d64 -Xms128m -Xmx2048m -XX:MaxPermSize=218M "
JMX=" -Djava.rmi.server.hostname=$IP -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false "
LOG=" -Dlogback.configurationFile=config/logback.xml "
java $JVM $JMX $LOG -Djava.net.preferIPv4Stack=true -jar $APP &
disown
