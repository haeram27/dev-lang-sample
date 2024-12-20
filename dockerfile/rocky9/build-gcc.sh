#!/bin/bash
IMAGE_NAME=gcc-rocky
OS_VERSION=9.5
docker image rm ${IMAGE_NAME}:${OS_VERSION} &>/dev/null

export DOCKER_BUILDKIT=1
docker build \
    --progress=plain \
    --no-cache \
    --build-arg app_home=/opt/myapp \
    --build-arg app_user=myapp \
    --build-arg app_uid=777 \
    --build-arg mongo_ver=8.0.3 \
    --build-arg pgsql_ver=17 \
    --network host \
    -t ${IMAGE_NAME}:${OS_VERSION} \
    -f ./${IMAGE_NAME}-${OS_VERSION}.dockerfile .
