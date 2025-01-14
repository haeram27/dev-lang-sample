#!/usr/bin/env bash
OS_VERSION=8.7
docker run -it --rm -u0 -v ${PWD}:${PWD} -w ${PWD} h27rocky:${OS_VERSION}
