
<> 빌드용 container 실행
opensource 디렉토리 내에 openssl과 mongoc driver 소스를 위치시키고 빌드용 container를 실행한다.

# command
$ mkdir work && cd work
$ docker run --rm -it -v ${PWD}:/work gcc-rocky:9.5 ./build-oss.sh

# dependency
curl과 mongoc driver는 빌드시 openssl library를 참조함

----------------------------
| 참조 여부      | openssl |
----------------------------
| curl           |    O    |
----------------------------
| mongo-c-driver |    O    |
----------------------------
| haproxy        |    O    |
----------------------------



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
    -Wl,-rpath,/opt/myapp/lib -Wl,--enable-new-dtags \
    zlib enable-camellia enable-seed enable-rfc3779 enable-cms enable-md2 enable-rc5 enable-ktls no-mdc2 no-ec2m no-sm2 no-sm4
$ make -j16

# options
--prefix
    기본값: /usr/local
    설치타임: 설치시 참조되는 각 기본 경로의 최상위 path
	예를 들어, make install 실행한 경우 openssl 파일들은 별도의 지정이 없는 경우 다음 기본 값으로 설치 된다.
    openssl 실행 파일은 <prefix>/bin
    openssl 라이브러리 파일은 <prefix>/lib
    openssl 설정 파일은 <prefix>/ssl

--libdir
    기본값: <prefix>/lib
    설치타임: make install 실행시 openSSL 라이브러리 파일(libssl.so, libcrypto.so 등)이 설치될 디렉터리를 지정
    --prefix 옵션을 사용하는 기본 라이브러리 경로보다 우선 사용 된다.

	예시: ./config --libdir=/path/to/lib
	/path/to/lib/libssl.so
	/path/to/lib/libcrypto.so

--openssldir
    기본값: <prefix>/ssl
    설치타임: OpenSSL의 설정 파일(openssl.cnf)과 데이터 파일(예: 인증서(/certs), 키 등)의 설치 경로 지정
    실행타임: openssl executable과 library(libssl.so libcrypto.so)가 실행시 설정파일(openssl.cnf) 파일을 찾는 경로 
    실행 파일의 설치 경로와는 무관합니다.

	예시: --openssldir=/path/to/ssl
    /path/to/ssl/openssl.cnf
    /path/to/ssl/certs/

--api=x.y[.z]
    기본값: source release version
    https://github.com/openssl/openssl/blob/master/INSTALL.md#api-level
    Build the OpenSSL libraries(libssl.so/a libcrypto.so/a NOT openssl executable) to support
    the API for the specified version.
    If you want use latest API level of release then DO NOT set this options.
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

-Wl,--enable-new-dtags
    https://sourceware.org/binutils/docs/ld/Options.html
    기본값: disable, ld에는 --disable-new-dtags 옵션이 기본으로 지정된다.
    gnu linker(ld)의 옵션
    ELF의 dynamic section에 new dynamic tag를 생성하고, old tags는 생성하지 않는다.
    dtag 중 old tag인 RPATH 를 RUNPATH 로 변경하는 목적으로 사용한다.
    RUNPATH는 modern linux에서 사용하는 dtags이다.

-Wl,-rpath,<runtime.so.search.path>
    기본값: 없음
    https://wiki.openssl.org/index.php/Compilation_and_Installation#Using_RPATHs

    실행타임: openssl executable이 실행할 때 library(libssl.so libcrypto.so)를 찾는 경로
    openssl ELF의 RUNPATH tag에 ossl 라이브러리 경로를 지정한다.

    -Wl은 gnu compiler(gcc/g++)에게 링커(ld)에게 전달할 옵션임을 명시하는 옵션이다.
    -Wl,--enable-new-dtags 옵션과 합쳐서 다음과 같이 하나의 -Wl로 묶을 수 있다.
    -Wl,--enable-new-dtags,-rpath,<runtime.so.search.path> 

    이 옵션을 지정 하지 않으면 openssl 실행시 ldconfig에 설정된 시스템 기본 library의 경로를 참조하게 된다.
    a. openssl ELF의 RUNPATH tag 확인 방법
    $ readelf -d openssl
    b. ldconfig cache 내용 출력: ldconfig 시스템 기본 라이브러리 경로 확인
    $ ldconfig -p | grep libssl

refers for version migration
    https://docs.openssl.org/3.4/man7/ossl-guide-migration
    https://github.com/curl/curl/blob/master/docs/INSTALL-CMAKE.md
    https://wiki.openssl.org/index.php/OpenSSL_3.0#Other_notable_deprecations_and_changes


<> curl build (version 8.11.1)
# build command
$ git clone --depth 1 --branch curl-8_11_1 https://github.com/curl/curl.git curl-8.11.1
$ cd curl-8.11.1
$ mkdir out && cd out
$ cmake
    -DOPENSSL_ROOT_DIR=${OPENSSL_FULL_DIR} \
    -DOPENSSL_INCLUDE_DIR=${OPENSSL_FULL_DIR}/include \
    -DOPENSSL_SSL_LIBRARY=${OPENSSL_FULL_DIR}/libssl.so \
    -DOPENSSL_CRYPTO_LIBRARY=${OPENSSL_FULL_DIR}/libcrypto.so \
    -DCMAKE_INSTALL_RPATH=/opt/myapp/lib  \
    ..
$ make -j16
$ make install
$ mkdir include
$ cp -r /usr/local/include/curl include

# options
## https://github.com/curl/curl/blob/master/docs/INSTALL-CMAKE.md
-DCMAKE_INSTALL_RPATH=<runtime.so.search.path>
 ELF에 RUNPATH dtag 설정
-DOPENSSL_ROOT_DIR=${OPENSSL_FULL_DIR}
 OPENSSL_ROOT_DIR경로는 openssl release source 디렉토리 경로이며, 빌드가 완료되어 있어야 한다.
 curl 빌드 중 이 경로에서 openssl shared object/header를 자동으로 찾는다.
-DOPENSSL_INCLUDE_DIR=${OPENSSL_FULL_DIR}/include
 openssl header 파일 참조 경로, 이 변수의 header 경로는 OPENSSL_ROOT_DIR 보다 우선한다.
-DOPENSSL_SSL_LIBRARY=${OPENSSL_FULL_DIR}/libssl.so
 openssl libssl.so 파일 참조 경로, 이 변수의 경로는 OPENSSL_ROOT_DIR 경로 보다 우선 한다.
-DOPENSSL_CRYPTO_LIBRARY=${OPENSSL_FULL_DIR}/libcrypto.so
 openssl libcrypto.so 파일 참조 경로, 이 변수의 경로는 OPENSSL_ROOT_DIR 경로 보다 우선 한다.

# openssl lib/header 경로 지정 방법
curl은 openssl shared object와 header를 참조하여 빌드된다.
일반적으로 빌드 완료된 openssl 배포본 디렉토리를 OPENSSL_ROOT_DIR에 지정하면 해당 경로에서 자동으로 so와 header를 찾는다.
별도로 so/header 경로를 지정하고자 하는 경우에만 추가로로 다음 세가지 옵션을 사용한다.
OPENSSL_INCLUDE_DIR
PENSSL_SSL_LIBRARY
OPENSSL_CRYPTO_LIBRARY


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
    -DCMAKE_INSTALL_RPATH=/opt/myapp/lib  \
    ..
$ make -j16
$ make install
$ mkdir include
$ cp -r /usr/local/include/libbson-1.0 include
$ cp -r /usr/local/include/libmongoc-1.0 include

# options
## https://www.mongodb.com/docs/languages/c/c-driver/current/install-from-source/
-DCMAKE_INSTALL_RPATH=<runtime.so.search.path>
 ELF에 RUNPATH dtag 설정
-DOPENSSL_ROOT_DIR=${OPENSSL_FULL_DIR}
 OPENSSL_ROOT_DIR경로는 openssl release source 디렉토리 경로이며, 빌드가 완료되어 있어야 한다.
 curl 빌드 중 이 경로에서 openssl shared object/header를 자동으로 찾는다.
-DOPENSSL_INCLUDE_DIR=${OPENSSL_FULL_DIR}/include
 openssl header 파일 참조 경로, 이 변수의 header 경로는 OPENSSL_ROOT_DIR 보다 우선한다.
-DOPENSSL_SSL_LIBRARY=${OPENSSL_FULL_DIR}/libssl.so
 openssl libssl.so 파일 참조 경로, 이 변수의 경로는 OPENSSL_ROOT_DIR 경로 보다 우선 한다.
-DOPENSSL_CRYPTO_LIBRARY=${OPENSSL_FULL_DIR}/libcrypto.so
 openssl libcrypto.so 파일 참조 경로, 이 변수의 경로는 OPENSSL_ROOT_DIR 경로 보다 우선 한다.

# openssl lib/header 경로 지정 방법
curl은 openssl shared object와 header를 참조하여 빌드된다.
일반적으로 빌드 완료된 openssl 배포본 디렉토리를 OPENSSL_ROOT_DIR에 지정하면 해당 경로에서 자동으로 so와 header를 찾는다.
별도로 so/header 경로를 지정하고자 하는 경우에만 추가로로 다음 세가지 옵션을 사용한다.
OPENSSL_INCLUDE_DIR
PENSSL_SSL_LIBRARY
OPENSSL_CRYPTO_LIBRARY
