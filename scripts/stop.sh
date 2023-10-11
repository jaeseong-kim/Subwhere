#!/usr/bin/env bash

SERVER_PORT=8083

echo "> 8083에서 구동 중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${SERVER_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> 구동 중인 애플리케이션 발견, 프로그램을 종료합니다."
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi