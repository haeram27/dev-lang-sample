#!/usr/bin/env bash

docker run -it --rm -u0 -v ${PWD}:${PWD} -w ${PWD} h27rocky:9.5
