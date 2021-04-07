#!/bin/bash

DOCKER_APP_NAME=muyaho-api

aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 815637561247.dkr.ecr.ap-northeast-2.amazonaws.com

EXIST_BLUE=$(docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml ps | grep Up)

if [ -z "$EXIST_BLUE" ]; then
    docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d --build
    sleep 20
    docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml down
else
    docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d --build
    sleep 20
    docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml down
fi
