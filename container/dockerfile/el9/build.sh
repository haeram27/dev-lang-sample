#!/bin/bash
IMAGE_NAME=h27rocky
OS_VERSION=9.5
MONGODB_VERSION=7.0.18
docker image rm ${IMAGE_NAME}:${OS_VERSION} &>/dev/null

export DOCKER_BUILDKIT=1
docker build \
    --no-cache \
    --progress=plain \
    --build-arg app_home=/opt/myapp \
    --build-arg app_user=myapp \
    --build-arg app_uid=777 \
    --build-arg mongo_ver=${MONGODB_VERSION} \
    --build-arg pgsql_ver=17 \
    --network host \
    -t ${IMAGE_NAME}:${OS_VERSION} \
    -f ./${IMAGE_NAME}-${OS_VERSION}.dockerfile . \
    2>&1 | tee docker.build.log
