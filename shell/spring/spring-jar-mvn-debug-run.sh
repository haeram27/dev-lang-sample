#!/usr/bin/env bash
: ${SPRING_MOD_PATH:=/path/to/project}
: ${SPRING_JAR_FILE:=my-spring-web.jar}

cd ${SPRING_MOD_PATH}
mvn -e package -Dcheckstyle.skip -Dpmd.skip=true -Dcpd.skip=true -Dmaven.test.skip=true
java -jar -Xms512m -Xmx2048m -Dmax.threads=512 -Dspring.profiles.active=dev  ${SPRING_MOD_PATH}/target/${SPRING_JAR_FILE} | tee /tmp/spring-debug-run-${SPRING_JAR_FILE}-$(date -u +%Y%m%d%H%M%S%N).log
