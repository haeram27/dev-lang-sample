# native opensource 빌더

본 도구는 openssl, curl, haproxy, mongo-c-driver, redis 등 opensource를 소스로 부터 자체 빌드 하는 것을 목적으로 한다.

각 opensource의 소스는 github 또는 자체 배포 소스를 다운로드하여 사용하므로 반드시 `인터넷 접속이 가능한 환경`에서 실행하여야 한다.

- 빌드 가능 oss
  - openssl
  - curl
  - haproxy
  - mongo-c-driver
  - redis

## 빌드 과정

1. 요구되는 OS의 빌드용 컨테이너 생성
2. 스크립트를 실행하여 각 opensource 빌드

## OS 빌드용 컨테이너 생성

위치: dockerfile/\<os-name\>

현재 rhel 9 base의 gcc 빌드용 컨테이너 구성 코드가 작성되어 있다.  
필요시 위 경로를 참조하여 빌드 컨테이너를 구성한다.

### `dockerfile/el9`의 파일 설명

- `build.gcc.sh`
  - container image 빌드용 스크립트. 이 스크립트 실행하면 컨테이너 이미지가 생성된다.
- `run.rocky.container.sh`
  - 테스트용 container 실행 스크립트.
- `gcc-docker-9.5.dockerfile`
  - rhel 9.5 base의 gcc 빌드 환경 컨테이너 이미지 생성용 dockerfile
- `buildtemp`
  - 이미지 빌드 과정에서 참조되는 파일들

## openssl, curl, haproxy, mongo-c-driver 빌드

openssl은 여러 다른 oss에서 참조되므로 openssl이 변경되면 이를 참조하는 다른 oss도 재 빌드 되어야 한다. 현재 curl, haproxy, mongo-c-driver는 openssl을 참조 하고 있으므로 이들은 한번에 그룹으로 빌드한다.

### 버전 수정

`build-oss.sh` 내 각 oss의 버전 넘버를 지정한다.

### 관련 파일

- `build.sh`
  - openssl과 이를 참조하는 oss들을 빌드하는 스크립트.
  - 실행결과로 `oss-<os-name>.tgz` 파일이 생성된다.
- `build-openssl-group.sh`
  - 실제 oss를 빌드하는 스크립트. oss의 버전이 변경되는 경우 이 스크립트에서 버전 값을 수정해 주어야 한다.
- `clear.sh`
  - 빌드 중간 생성물을 삭제하는 스크립트. 여러번 재빌드를 시도하는 경우 사용.

## redis

redis 빌드

### 버전 수정

`build-redis.sh` 내 각 oss의 버전 넘버를 지정한다.

### 관련 파일

- `build-redis.sh`
  - redis를 빌드하여 `redis-out-${REDIS_VERSION}.tgz` 파일을 생성한다.
