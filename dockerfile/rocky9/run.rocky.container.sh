#!/usr/bin/env bash
OS_VERSION=9.5
docker run -it --rm -u0 -v ${PWD}:${PWD} -w ${PWD} h27rocky:${OS_VERSION}
