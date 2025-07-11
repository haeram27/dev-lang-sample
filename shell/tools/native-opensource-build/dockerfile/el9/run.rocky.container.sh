#!/usr/bin/env bash
: ${IMAGE_NAME:=gcc-rocky}
: ${OS_VERSION:=9.5}
docker run -it --rm -u0 -v ${PWD}:${PWD} -w ${PWD} ${IMAGE_NAME}:${OS_VERSION}
