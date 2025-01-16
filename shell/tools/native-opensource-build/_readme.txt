
<> 빌드용 container 실행
opensource 디렉토리 내에 openssl과 mongoc driver 소스를 위치시키고 빌드용 container를 실행한다.

# command
$ mkdir work && cd work
$ docker run --rm -it -v ${PWD}:/work gcc-rocky:9.5 ./build-oss.sh

# dependency
curl과 mongoc driver는 빌드시 openssl library를 참조함
openssl
├── curl
└── mongo-c-driver


<> openssl build (since 3.x.x)
openssl 빌드는 빌드 결과로 소스 원본 디렉토리(예 include)에 추가 header 소스를 생성한다.
그러므로 별도의 빌드 결과용 디렉토리를 생성하지 말고 소스 top 디렉토리에서 config 및 make를 실행한다.

# build command
$ git clone --depth 1 --branch openssl-3.4.0 https://github.com/openssl/openssl.git openssl-3.4.0
$ cd openssl-3.4.0
$ ./config \
    --prefix=/opt/myapp \
    --libdir=/opt/myapp/lib \
    --openssldir=/opt/myapp/conf/ssl \
    --api=3.0 \
    zlib enable-camellia enable-seed enable-rfc3779 enable-cms enable-md2 enable-rc5 enable-ktls no-mdc2 no-ec2m no-sm2 no-sm4
$ make -j16$ make -j16

--prefix (기본값: /usr/local)
    설치시 참조되는 각 기본 경로의 최상위 path를 지정
	예를 들어, openssl 실행 파일은 <prefix>/bin 경로에 설치 된다.

--libdir (기본값: <prefix>/lib)
	OpenSSL 라이브러리 파일(libssl.so, libcrypto.so 등)이 설치될 디렉터리를 지정

	예시: ./config --libdir=/path/to/lib
	/path/to/lib/libssl.so
	/path/to/lib/libcrypto.so

--openssldir의 역할 (기본값: <prefix>/ssl )
    OpenSSL의 설정 파일(openssl.cnf)과 데이터 파일(예: 인증서(/certs), 키 등)의 기본 경로를 지정합니다.
    실행 파일의 설치 경로와는 무관합니다.

	예시: --openssldir=/path/to/ssl
    /path/to/ssl/openssl.cnf
    /path/to/ssl/certs/

--api=x.y[.z]
    https://github.com/openssl/openssl/blob/master/INSTALL.md#api-level
    Build the OpenSSL libraries(libssl.so/a libcrypto.so/a NOT openssl executable) to support
    the API for the specified version.
    WARNING!
    This api option only affects to libraries(libssl.so/a libcrypto.so/a NOT openssl executable).
    Api version option defines MAX(limited) LEVEL of library api, so if you set as 1.1
    then opessl libraries has apis level 1.1 and below only. 
    openssl executable can NOT find apis over api level of 1.1 with this libraries.
    openssl excutable alway find it's release verion of api
    so that excutable can NOT run normally with limited libraries. 
    
    If "no-deprecated" option is also given with --api option,
    then deprecated apis under designated version are not supported.
    For example,
    --api=1.1 no-deprecated

refers for version migration
    https://docs.openssl.org/3.4/man7/ossl-guide-migration
    https://wiki.openssl.org/index.php/OpenSSL_3.0#Other_notable_deprecations_and_changes

<> curl build (version 8.11.1)
$ git clone --depth 1 --branch curl-8_11_1 https://github.com/curl/curl.git curl-8.11.1
$ cd curl-8.11.1
$ mkdir out && cd out
$ cmake -DOPENSSL_ROOT_DIR=${OPENSSL_FULL_DIR} \
    -DOPENSSL_INCLUDE_DIR=${OPENSSL_FULL_DIR}/include \
    -DOPENSSL_SSL_LIBRARY=${OPENSSL_FULL_DIR}/libssl.so \
    -DOPENSSL_CRYPTO_LIBRARY=${OPENSSL_FULL_DIR}/libcrypto.so \
    #-DCMAKE_INSTALL_RPATH=/opt/myapp/lib  \
    ..$ make -j16
$ make install
$ mkdir include
$ cp -r /usr/local/include/curl include

## https://github.com/curl/curl/blob/master/docs/INSTALL-CMAKE.md
OPENSSL_ROOT_DIR = openssl project directory including build result
OPENSSL_INCLUDE_DIR = openssl including build result



<> mongoc driver build (version 1.29.0)
# build command 
$ git clone --depth 1 --branch 1.29.0 https://github.com/mongodb/mongo-c-driver.git mongo-c-driver-1.29.0
$ cd mongo-c-driver-1.29.0
$ mkdir out && cd out
$ cmake -DMONGOC_ENABLE_MONGODB_AWS_AUTH=OFF \
    -DENABLE_SASL=OFF -DENABLE_ZSTD=OFF \
    -DOPENSSL_ROOT_DIR=${OPENSSL_FULL_DIR} \
    -DOPENSSL_INCLUDE_DIR=${OPENSSL_FULL_DIR}/include \
    -DOPENSSL_SSL_LIBRARY=${OPENSSL_FULL_DIR}/libssl.so \
    -DOPENSSL_CRYPTO_LIBRARY=${OPENSSL_FULL_DIR}/libcrypto.so \
    #-DCMAKE_INSTALL_RPATH=/opt/myapp/lib  \
    ..
$ make -j16
$ make install
$ mkdir include
$ cp -r /usr/local/include/libbson-1.0 include
$ cp -r /usr/local/include/libmongoc-1.0 include

OPENSSL_ROOT_DIR = openssl project directory including build result
OPENSSL_INCLUDE_DIR = openssl including build result



