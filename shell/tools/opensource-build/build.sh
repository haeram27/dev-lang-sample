#!/usr/bash
docker run --rm -it -u -v ${PWD}:/oss -w /oss gcc-rocky:9.5 bash ./build-oss.sh
