#!/bin/bash
set -x


################
# run this script in a container
################
# docker run --rm -it -v ${PWD}:/work -w /work gcc-rocky:9.5 bash
# ./build-oss-cppm.sh 2>&1 | tee build.log

################
# mutable
################
OS_TYPE=el9

# openssl
BUILD_OPENSSL=yes
OPENSSL_VERSION=3.4.1
OPENSSL_GIT_TAG=openssl-${OPENSSL_VERSION}

# curl
BUILD_CURL=yes
CURL_VERSION=8.11.1
CURL_GIT_TAG=curl-8_11_1

# mongo-c-driver
BUILD_MONGOCDRV=yes
MONGOCDRV_VERSION=1.29.1
MONGOCDRV_GIT_TAG=1.29.1

# haproxy
BUILD_HAPROXY=yes
HAPROXY_VERSION=3.0.6
HAPROXY_GIT_TAG=v${HAPROXY_VERSION}

# collect
COLLECT_LIBS=yes
COLLECTION_ROOT_DIR=oss/server


################
# immutable
################
WORK_DIR=${PWD}
OPENSSL_DIR=openssl-${OPENSSL_VERSION}
OPENSSL_FULL_DIR=${WORK_DIR}/${OPENSSL_DIR}
CURL_DIR=curl-${CURL_VERSION}
MONGOCDRV_DIR=mongo-c-driver-${MONGOCDRV_VERSION}
HAPROXY_DIR=haproxy-${HAPROXY_VERSION}

check_root_euid() {
  if [[ ${EUID} -ne 0 ]]; then
    echo "${FUNCNAME}: Please run this script as root"
    exit 1
  fi
}
check_root_euid

#####################
# collect bins
#####################
if [[ ${COLLECT_LIBS} = "yes" ]]; then
    cd ${WORK_DIR}

    ## remove existing collection result
    rm -rf oss oss-${OS_TYPE}.tgz

    # openssl
    mkdir -p ${COLLECTION_ROOT_DIR}/external/STATIC_BIN/${OPENSSL_DIR}/${OS_TYPE}
    mkdir -p ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${OPENSSL_DIR}/${OS_TYPE}/ossl-modules
    cp -a ${OPENSSL_DIR}/apps/openssl ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${OPENSSL_DIR}/${OS_TYPE}
    cp -a ${OPENSSL_DIR}/apps/openssl.cnf ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${OPENSSL_DIR}/${OS_TYPE}
    cp -a ${WORK_DIR}/openssl.cnf.legacy ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${OPENSSL_DIR}/${OS_TYPE}/openssl.cnf
    cp -a ${OPENSSL_DIR}/libcrypto.so* ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${OPENSSL_DIR}/${OS_TYPE}
    cp -a ${OPENSSL_DIR}/libssl.so* ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${OPENSSL_DIR}/${OS_TYPE}
    cp -a ${OPENSSL_DIR}/providers/legacy.so ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${OPENSSL_DIR}/${OS_TYPE}/ossl-modules
    cp -a ${OPENSSL_DIR}/libcrypto.a ${COLLECTION_ROOT_DIR}/external/STATIC_BIN/${OPENSSL_DIR}/${OS_TYPE}
    cp -a ${OPENSSL_DIR}/libssl.a ${COLLECTION_ROOT_DIR}/external/STATIC_BIN/${OPENSSL_DIR}/${OS_TYPE}
    cp -a ${OPENSSL_DIR}/providers/liblegacy.a ${COLLECTION_ROOT_DIR}/external/STATIC_BIN/${OPENSSL_DIR}/${OS_TYPE}

    mkdir -p ${COLLECTION_ROOT_DIR}/include/${OPENSSL_DIR}/${OS_TYPE}
    cp -r ${OPENSSL_DIR}/include/crypto ${COLLECTION_ROOT_DIR}/include/${OPENSSL_DIR}/${OS_TYPE}
    cp -r ${OPENSSL_DIR}/include/openssl ${COLLECTION_ROOT_DIR}/include/${OPENSSL_DIR}/${OS_TYPE}


    # curl
    if [[ ${BUILD_CURL} = "yes" ]]; then
        mkdir -p ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${CURL_DIR}/${OS_TYPE}
        cp -a ${CURL_DIR}/out/lib/libcurl.so* ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${CURL_DIR}/${OS_TYPE}

        mkdir -p ${COLLECTION_ROOT_DIR}/include/${CURL_DIR}/${OS_TYPE}
        cp -a ${CURL_DIR}/out/include/curl/*.h ${COLLECTION_ROOT_DIR}/include/${CURL_DIR}/${OS_TYPE}
    fi

    # mongoc
    if [[ ${BUILD_MONGOCDRV} = "yes" ]]; then
        mkdir -p ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${MONGOCDRV_DIR}/${OS_TYPE}
        mkdir -p ${COLLECTION_ROOT_DIR}/external/STATIC_BIN/${MONGOCDRV_DIR}/${OS_TYPE}
        cp -a ${MONGOCDRV_DIR}/out/src/libbson/libbson-1.0.so* ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${MONGOCDRV_DIR}/${OS_TYPE}
        cp -a ${MONGOCDRV_DIR}/out/src/libmongoc/libmongoc-1.0.so* ${COLLECTION_ROOT_DIR}/external/SHARED_BIN/${MONGOCDRV_DIR}/${OS_TYPE}
        cp -a ${MONGOCDRV_DIR}/out/src/libbson/libbson-static-1.0.a ${COLLECTION_ROOT_DIR}/external/STATIC_BIN/${MONGOCDRV_DIR}/${OS_TYPE}
        cp -a ${MONGOCDRV_DIR}/out/src/libmongoc/libmongoc-static-1.0.a ${COLLECTION_ROOT_DIR}/external/STATIC_BIN/${MONGOCDRV_DIR}/${OS_TYPE}

        mkdir -p ${COLLECTION_ROOT_DIR}/include/${MONGOCDRV_DIR}/${OS_TYPE}
        cp -a ${MONGOCDRV_DIR}/out/include/* ${COLLECTION_ROOT_DIR}/include/${MONGOCDRV_DIR}/${OS_TYPE}
    fi

    # haproxy
    if [[ ${BUILD_HAPROXY} = "yes" ]]; then
        mkdir -p ${COLLECTION_ROOT_DIR}/external/service/${OS_TYPE}/haproxy/${HAPROXY_VERSION}/bin
        cp -a ${HAPROXY_DIR}/haproxy ${COLLECTION_ROOT_DIR}/external/service/${OS_TYPE}/haproxy/${HAPROXY_VERSION}/bin
    fi

    # collect
    if [[ $(find oss | wc -l) -gt 400 ]]; then
        sudo chown -R root: oss
        tree oss
        tar zcvf oss-${OS_TYPE}.tgz oss
    fi
fi
