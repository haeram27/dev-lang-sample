#!/bin/bash
find . -maxdepth 1 -name openssl-* -type d -exec sudo rm -rf {} \;
find . -maxdepth 1 -name curl-* -type d -exec sudo rm -rf {} \;
find . -maxdepth 1 -name mongo-* -type d -exec sudo rm -rf {} \;
find . -maxdepth 1 -name oss -type d -exec sudo rm -rf {} \;
sudo rm -f ./oss-*.tgz
