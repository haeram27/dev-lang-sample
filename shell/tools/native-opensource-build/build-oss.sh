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
OPENSSL_VERSION=3.4.0
OPENSSL_GIT_TAG=openssl-3.4.0

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
HAPROXY_VERSION=3.0.7
HAPROXY_GIT_TAG=v3.0.0

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

## remove build results
rm -rf oss oss-${OS_TYPE}.tgz

## openssl
if [[ ${BUILD_OPENSSL} = "yes" ]]; then
    if [[ ! -d ${OPENSSL_DIR} ]]; then
        git clone --depth 1 --branch ${OPENSSL_GIT_TAG} https://github.com/openssl/openssl.git ${OPENSSL_DIR}
    fi

    if [[ -d ${OPENSSL_DIR} ]]; then
        cd ${OPENSSL_DIR}
        git restore :/ && git clean -ffdx
        set -e
    ## openssl config options
    #        --prefix=/opt/myapp
    #        --libdir=/opt/myapp/lib
    #        --openssldir=/opt/myapp/conf/ssl
    #        --api=3.0
    #        -Wl,--enable-new-dtags,-rpath,<runtime.so.search.path>
        ./config \
            --libdir=/opt/ahnlab/cpp/bin \
            --openssldir=/opt/ahnlab/cpp/bin \
            -Wl,--enable-new-dtags,-rpath,/opt/ahnlab/cpp/bin \
            zlib enable-camellia enable-seed enable-rfc3779 enable-cms enable-md2 enable-rc5 enable-ktls \
            no-mdc2 no-ec2m no-sm2 no-sm4
        make -j$(nproc)
        set +e
        cd ${WORK_DIR}
    else
        echo "Error: can NOT find opsenssl directory"
        exit 1
    fi
fi

## curl
if [[ ${BUILD_CURL} = "yes" ]]; then
    if [[ ! -d ${CURL_DIR} ]]; then
        git clone --depth 1 --branch ${CURL_GIT_TAG} https://github.com/curl/curl.git ${CURL_DIR}
    fi

    if [[ -d ${CURL_DIR} ]]; then
        cd ${CURL_DIR}
        git restore :/ && git clean -ffdx
        mkdir out && cd out
        set -e
    ## curl cmake options
    #        -DCMAKE_INSTALL_RPATH=/opt/myapp/lib
    # path to openssl: select 1 of 2 way
    #        -DOPENSSL_ROOT_DIR=${OPENSSL_FULL_DIR}
    # or
    #        -DOPENSSL_INCLUDE_DIR=${OPENSSL_FULL_DIR}/include
    #        -DOPENSSL_SSL_LIBRARY=${OPENSSL_FULL_DIR}/libssl.so
    #        -DOPENSSL_CRYPTO_LIBRARY=${OPENSSL_FULL_DIR}/libcrypto.so
        cmake -DOPENSSL_ROOT_DIR=${OPENSSL_FULL_DIR} \
            -DCMAKE_INSTALL_RPATH=/opt/ahnlab/cpp/bin \
            ..
        make -j$(nproc)
        make install
        set +e
        mkdir include
        cp -r /usr/local/include/curl include
        cd ${WORK_DIR}
    else
        echo "Error: can NOT find curl directory"
        exit 1
    fi
fi

# mongo-c-driver
if [[ ${BUILD_MONGOCDRV} = "yes" ]]; then
    if [[ ! -d ${MONGOCDRV_DIR} ]]; then
        git clone --depth 1 --branch ${MONGOCDRV_GIT_TAG} https://github.com/mongodb/mongo-c-driver.git ${MONGOCDRV_DIR}
    fi

    if [[ -d ${MONGOCDRV_DIR} ]]; then
        cd ${MONGOCDRV_DIR}
        git restore :/ && git clean -ffdx
        mkdir out && cd out
        set -e
    ## mongoc cmake options
    #        -DCMAKE_INSTALL_RPATH=/opt/myapp/lib
    # path to openssl: select 1 of 2 way
    #        -DOPENSSL_ROOT_DIR=${OPENSSL_FULL_DIR}
    # or
    #        -DOPENSSL_INCLUDE_DIR=${OPENSSL_FULL_DIR}/include
    #        -DOPENSSL_SSL_LIBRARY=${OPENSSL_FULL_DIR}/libssl.so
    #        -DOPENSSL_CRYPTO_LIBRARY=${OPENSSL_FULL_DIR}/libcrypto.so
        cmake -DMONGOC_ENABLE_MONGODB_AWS_AUTH=OFF \
            -DENABLE_SASL=OFF -DENABLE_ZSTD=OFF \
            -DOPENSSL_ROOT_DIR=${OPENSSL_FULL_DIR} \
            -DCMAKE_INSTALL_RPATH=/opt/ahnlab/cpp/bin \
            ..
        make -j$(nproc)
        make install
        set +e
        mkdir include
        cp -r /usr/local/include/libbson-1.0 include
        cp -r /usr/local/include/libmongoc-1.0 include
        cd ${WORK_DIR}
    else
        echo "Error: can NOT find mongo-c-driver directory"
        exit 1
    fi
fi


# haproxy
if [[ ${BUILD_HAPROXY} = "yes" ]]; then
    if [[ ! -d ${HAPROXY_DIR} ]]; then
        git clone --depth 1 --branch ${HAPROXY_GIT_TAG} https://github.com/haproxy/haproxy.git ${HAPROXY_DIR}
    fi

    if [[ -d ${HAPROXY_DIR} ]]; then
        cd ${HAPROXY_DIR}
        git restore :/ && git clean -ffdx
        if ! dnf list --installed lua-devel; then
            dnf install -y lua-devel
        fi
        if ! dnf list --installed pcre2-devel; then
            dnf install -y pcre2-devel
        fi
        set -e
        # https://github.com/haproxy/haproxy/blob/v3.0.0/INSTALL
        make -j$(nproc) TARGET=linux-glibc \
            USE_LUA=1 \
            USE_PCRE2=1 \
            USE_OPENSSL=1 SSL_INC=${OPENSSL_FULL_DIR}/include SSL_LIB=${OPENSSL_FULL_DIR} \
            LDFLAGS="-Wl,--enable-new-dtags,-rpath,/opt/ahnlab/cpp/bin"
        #make install
        set +e
        cd ${WORK_DIR}
    else
        echo "Error: can NOT find haproxy directory"
        exit 1
    fi
fi

#####################
# collect bins
#####################
if [[ ${COLLECT_LIBS} = "yes" ]]; then
    cd ${WORK_DIR}

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

    # collect
    if [[ $(find oss | wc -l) -gt 400 ]]; then
        sudo chown -R root: oss
        tree oss
        tar zcvf oss-${OS_TYPE}.tgz oss
    fi
fi