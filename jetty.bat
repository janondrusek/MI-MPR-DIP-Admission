@ECHO OFF
set MAVEN_OPTS=-server -Xmx512m -Xms512m -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8791,server=y,suspend=n 

mvn -e clean jetty:run
