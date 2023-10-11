#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/subwhere/Subwhere
PROJECT_NAME=subwhere

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls $REPOSITORY/*.jar | tail -n 1)

echo "> JAR_NAME : $JAR_NAME"

echo "> $JAR_NAME 에 실행 권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

SERVER_PORT=8083

echo "> $JAR_NAME 를 포트 번호:8083로 실행합니다."

nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,/home/ec2-user/subwhere/application-oauth.properties,\
/home/ec2-user/subwhere/application-api.properties,/home/ec2-user/subwhere/application-real-db.properties \
$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &