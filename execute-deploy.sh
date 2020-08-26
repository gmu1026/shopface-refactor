#!/bin/bash
CURRENT_PID=$(pgrep -f shopface)

if [ -z $CURRENT_PID ]; then
	echo "> 현재 구동중인 애플리케이션 없음"
else
	echo "> kill -15 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 15
fi

echo "> 애플리케이션 배포"

JAR_NAME=$(ls | grep 'shopface' | tail -n 1)

echo "> JAR name: $JAR_NAME"

nohup java -jar -Dspring.config.location=/home/ec2-user/properties/shopface-refactor/application-db.yml,/home/ec2-user/properties/shopface-refactor/application-aws.yml,/home/ec2-user/properties/shopface-refactor/application-mail.properties,classpath:application.yml $JAR_NAME


