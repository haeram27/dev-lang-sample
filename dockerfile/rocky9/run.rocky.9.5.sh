#!/usr/bin/env bash

docker run -it --rm -u0 -v ${PWD}:${PWD} -w ${PWD} h27-rocky:9.5
