#!/bin/bash
: ${IMAGE_NAME:=gcc-rocky}
: ${OS_VERSION:=9.5}

if docker image inspect ${IMAGE_NAME}:${OS_VERSION} &>/dev/null; then
    read -p "${IMAGE_NAME}:${OS_VERSION} image is already exist. Do you want rebuild image? (y/n) " -n 1
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        docker image rm ${IMAGE_NAME}:${OS_VERSION} &>/dev/null
    else
        exit
    fi
fi

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
