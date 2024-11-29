#!/bin/bash
IMAGE_NAME=h27-rocky

docker image rm ${IMAGE_NAME}:9.5 &>/dev/null

export DOCKER_BUILDKIT=1
docker build \
    --no-cache \
    --build-arg app_home=/opt/myapp \
    --build-arg app_user=myapp \
    --build-arg app_uid=777 \
    --build-arg mongo_ver=8.0.3 \
    --build-arg pgsql_ver=17 \
    --network host \
    -t ${IMAGE_NAME}:9.5 \
    -f ./${IMAGE_NAME}-9.5.dockerfile .
