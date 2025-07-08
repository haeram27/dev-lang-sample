#!/usr/bin/env bash
: ${IMG_NAME:=h27rocky}
: ${OS_VERSION:=9.5}
docker run -it --rm -u0 -v ${PWD}:${PWD} -w ${PWD} ${IMG_NAME}:${OS_VERSION}
