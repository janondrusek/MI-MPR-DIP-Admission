@ECHO OFF
set MAVEN_OPTS=-server -XX:MaxPermSize=256m -Xmx768m -Xms768m -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8791,server=y,suspend=n

mvn -e jetty:run
