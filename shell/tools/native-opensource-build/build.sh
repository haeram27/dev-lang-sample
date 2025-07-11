#!/bin/bash
: ${IMAGE_NAME:=gcc-rocky}
: ${OS_VERSION:=9.5}

check_http_conn() {
    local http_url=${1:-'https://github.com'}
    local req_timeout=2

    echo "try to connect ${http_url}"
    local http_code=$(curl -fksSL --connect-timeout ${req_timeout} -w "%{response_code}" ${http_url})
    local curl_ret=$?
    if [[ ${curl_ret} -eq 0 ]]; then
        echo "resp: ${http_code}"
        echo
        if [[ "${http_code}" == "200" || "${http_code}" == "202" ]]; then
            return 0
        else
            return 101
        fi
    else
        echo "curl connection failed to ${http_url}"
        return 102
    fi
    return 125
}

if ! check_http_conn; then
    echo "Fatal: can NOT connect to internet"
    exit 125
fi

if !docker image inspect ${IMAGE_NAME}:${OS_VERSION} &>/dev/null; then
    echo "Fatal: container image ${IMAGE_NAME}:${OS_VERSION} does NOT exitst. please prepare image first."
    exit 125
fi

docker run --rm -it -u0 -v ${PWD}:/oss -w /oss ${IMAGE_NAME}:${OS_VERSION} bash ./build-oss-cppm.sh
