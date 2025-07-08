#!/bin/bash
: ${REDIS_VERSION:=7.0.10}
: ${VALKEY_VERSION:=8.0.2}

git cl --branch ${REDIS_VERSION} https://github.com/redis/redis.git redis-${REDIS_VERSION}
#git cl --branch ${VALKEY_VERSION} https://github.com/valkey-io/valkey.git valkey-${VALKEY_VERSION}

cd redis-${REDIS_VERSION}
docker run --rm -it -u0 -v ${PWD}:/oss -w /oss gcc-rocky:9.5 bash -c "make test -j16"

mkdir -p out/bin
mkdir -p out/conf
cp redis.conf out/conf
cp src/redis-server out/bin
cp src/redis-cli out/bin
ln -sf redis-server out/bin/redis-check-aof
ln -sf redis-server out/bin/redis-check-rdb
ls -alR out

tar zcvf redis-out-${REDIS_VERSION}.tgz out
tar tf redis-out-${REDIS_VERSION}.tgz
