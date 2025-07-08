#!/usr/bin/env bash

##############
# DEBUGGING
##############
# set shell debugging options
# set -euvxo pipefail

# store last executed command
prev_cmd="none"
current_cmd="none"
trap 'prev_cmd=$current_cmd; current_cmd=$BASH_COMMAND' DEBUG

# print last executed command just before terminating script
destructor() {
    echo "### Print last executed commands"
    echo "prev_cmd: ${prev_cmd}"
    echo "current_cmd: ${current_cmd}"
}
trap destructor EXIT

##############
# VARS
##############
IMODE="imod.test"
VMODE_TIMEUTC="no"

##############
# VAR COMMON
##############
SCRIPT_DIR=$(dirname $(readlink -e "$0"))                       # $(cd $(dirname "$0") && pwd -P)
SCRIPT_FILE=$(basename $(test -L $0 && readlink $0 || echo $0)) # "${0##*/}"

# make random string
# RAND_STR=$(uuidgen | tr -d '-')$(date -u +%Y%m%d%H%M%S%N)

# ramdom string using urandom (len:32  maxlen: infinity)
# RAND_STR=$(cat /dev/urandom | tr -dc '[:alnum:]' | fold -w 32 | head -n 1)

# ramdom number using urandom (len:32  maxlen: infinity)
# RAND_NUM=$(cat /dev/urandom | tr -dc '0-9' | fold -w 32 | head -n 1)

NCORES=$(cat /proc/cpuinfo | grep cores | wc -l)

##############
# LOGS
##############
# syntax) echo_log "my messages"
: ${LOG_FILE:=/dev/null}
R='\033[0;31m' # RED
G='\033[0;32m' # GREEN
B='\033[0;34m'
N='\033[0m' # NORMAL (NO COLOR)

# if stdout is tty or pseudo-tty then return 0 (true)
isatty() {
    if [[ -t 1 ]]; then
        return 0 # true
    else
        return 1 # false
    fi
}

# syntax) echo_log "my messages"
# syntax) var=$(echo; ls /); echo_log "$var"
echo_log() {
    echo -e "$(date --rfc-3339=seconds) $1" | tee -a ${LOG_FILE}
}

echo_success() {
    if is_tty; then
        echo_log "${G}[SUCCESS]${N} $1"
    else
        echo_log "[SUCCESS] $1"
    fi
}

echo_failed() {
    if is_tty; then
        echo_log "${R}[FAILED]${N} $1"
    else
        echo_log "[FAILED] $1"
    fi
}

echo_error() {
    if is_tty; then
        echo_log "${R}[ERROR]${N} $1"
    else
        echo_log "[ERROR] $1"
    fi
}

echo_fatal() {
    if is_tty; then
        echo_log "${R}[FATAL]${N} $1"
    else
        echo_log "[FATAL] $1"
    fi
    exit 255
}

##############
# COMMON
##############
# root checking
check_root_euid() {
    if [[ ${EUID} -ne 0 ]]; then
        echo_failed "${FUNCNAME}: Please run this script as root"
        exit 1
    fi
}

# make space size 1 and leave printable character
sanitize_space() {
    echo "$1" |
        tr -d '\r\n' |
        tr -cd '[:print:]' |
        xargs
}

# short:: VAR=${VAR:-default}
check_var_unset_or_null_default() {
    if [[ !(-v TEST_VAR && -n ${TEST_VAR}) ]]; then
        TEST_VAR=default
        echo_log "TEST_VAR is set as \"${TEST_VAR}\" automatically."
    fi
}

check_command_exit_code() {
    #1 &&, || check current $?
    true && echo success || echo failed
    #2 check $? with test expression [[ ]]
    true
    [[ $? -eq 0 ]] && echo success || echo failed
    #3 combine #2 as one line
    [[ $(
        true &>/dev/null
        echo $?
    ) -eq 0 ]] && echo success || echo failed
}

print_args() {
    local idx=1
    local i

    for i; do
        echo "arg[$idx] = $i"
        ((idx++))
    done
}

help() {
    echo_log ${FUNCNAME} "$@"

    cat <<HELP
$(basename $0) Usage:
    -h        help.
    -q        rest rquest.
    -t        time.
    -u        set utc time when using with -t.
HELP
}

tests() {
    echo_log ${FUNCNAME} "$@"

    read -p "Do you want print Hello World? Are you sure? (y/n) " -n 1
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo_log "Hello World"
        echo_success "Hello World"
        echo_failed "Hello World"
    else
        echo $REPLY
    fi
}

gettime() {
    echo_log ${FUNCNAME} "$@"

    ## override VMODE_TIMEUTC only in ion
    #local VMODE_TIMEUTC="yes"
    if [[ "${VMODE_TIMEUTC}" == "yes" ]]; then
        echo 'UTC::'
        while :; do
            date -u --rfc-3339=ns
            sleep 0.1
        done
    else
        echo 'LOCAL::'
        while :; do
            date --rfc-3339=ns
            sleep 0.1
        done
    fi
}

retry_cmd() {
    local retry_count=1
    local max_retry=${1:-10}
    local sleep_interval=${2:-1}
    local funct=${3:-false}

    while :; do
        if [[ ${retry_count} -gt ${max_retry} ]]; then
            ## TODO: finally failed
            echo_log "max retries exceeded."
            return 1
        fi

        $(${funct})
        if [[ $? -eq 0 ]]; then
            ## TODO: success
            echo_success ${FUNCNAME}
            return
        fi

        ## TODO: failed nth try
        echo "[${retry_count}] ${funct} result is failed"
        ((retry_count++))
        sleep ${sleep_interval}
    done
}

##############
# parse config file
##############
parse-config-test() {
    # config with key and value
    local version_config="version=1.0.23"
    local test_version=$(echo $version_config | grep -oP '(?<=^version=)[.\d]+(?=\s*$)')
    echo $test_version

    local word_config="word=helloworld"
    local test_word=$(echo $word_config | grep -oP '(?<=^word=)\w+(?=\s*$)')
    echo $test_word

    # config with delimiter
    local db_config="rdb-oltp|127.0.0.1|9999"
    local test_port=$(echo $db_config | awk -F '|' '{print $3}')
    echo $test_port
}

##############
# generate automatically git message with time
##############
git-repo-push() {
    if [ -n "${GIT_REPO_PATH}" ]; then
        echo "GIT_REPO_PATH=${GIT_REPO_PATH}"
        pushd ${GIT_REPO_PATH} &>/dev/null
        if git st &>/dev/null; then
            git add .
            git commit --allow-empty-message -m "$(date -u --rfc-3339=ns)"
            git push
        else
            echo "GIT_REPO_PATH is NOT git repository"
        fi
        popd &>/dev/null
    else
        echo "GIT_REPO_PATH is empty"
    fi
}

git-repo-pull() {
    if [ -n "${GIT_REPO_PATH}" ]; then
        echo "GIT_REPO_PATH=${GIT_REPO_PATH}"
        pushd ${GIT_REPO_PATH} &>/dev/null
        if git status &>/dev/null; then
            git pull
        else
            echo "GIT_REPO_PATH is NOT git repository"
        fi
        popd &>/dev/null
    else
        echo "GIT_REPO_PATH is empty"
    fi
}

##############
# http handling
##############
check_http_conn() {
    echo_log ${FUNCNAME} "$@"

    local resp_file=\/tmp\/$(uuidgen | tr -d '-')$(date -u +%Y%m%d%H%M%S%N)
    local req_timeout=2
    local http_url='https://github.com'
    [[ -n $1 ]] && http_url=$1

    echo_log "try to connect ${http_url}"
    local http_code=$(curl -fksSL --connect-timeout ${req_timeout} -w "%{response_code}" -o ${resp_file} ${http_url})
    local curl_ret=$?
    if [[ ${curl_ret} -eq 0 ]]; then
        echo_log "resp: ${http_code}"
        echo
        if [[ "${http_code}" == "200" || "${http_code}" == "202" ]]; then
            return 0
        else
            return 201 #curl ok, http nok
        fi
    else
        echo_error "curl connection failed to ${http_url}"
        return 202 # curl nok
    fi
    return 255
}

rest_request() {
    echo_log ${FUNCNAME} "$@"

    local resp_file=\/tmp\/$(uuidgen | tr -d '-')$(date -u +%Y%m%d%H%M%S%N)
    local req_timeout=2
    local http_url='https://github.com'
    [[ -n $1 ]] && http_url=$1

    local http_code=$(curl -fksSL --connect-timeout ${req_timeout} -w "%{response_code}" -o ${resp_file} ${http_url})
    local curl_ret=$?
    if [[ ${curl_ret} -eq 0 ]]; then
        if [[ "${http_code}" == "200" ]]; then
            ## do something with ${RESP_FILE}
            echo ${resp_file}
            cat ${resp_file}
        else
            echo warning: httpcode is ${http_code} >&2
        fi
    else
        echo error: curl returns ${curl_ret} >&2
    fi

    [[ -f ${resp_file} ]] && (rm -f ${resp_file} &>/dev/null)
}

##############
# file handling
##############
# remove lines has pattern in files
FILE_PATH="test.txt"
PATTERN="^fs\.inotify\.max_user_watches"
remove_pattern_line_in_files() {
    if grep -qE "${PATTERN}" "${FILE_PATH}"; then
        sed -i "/${PATTERN}/d" "${FILE_PATH}"
    fi
}

##############
# main
##############
# Usage: ./script.sh -h
_main() {
    # IMODE(instruction) option is to seclect start function
    # VMODE(variable) option is to set flag variable of this script
    while getopts "thuq\?" opt; do
        case ${opt} in
        h | \?) IMODE="imod.help" ;;
        t) IMODE="imod.time" ;;
        q) IMODE="imod.rest" ;;
        u)
            echo "turn on UTC mode"
            VMODE_TIMEUTC="yes"
            ;;
        esac
    done

    #echo "IMODE=${IMODE}"
    case $IMODE in
    "imod.help") help ;;
    "imod.test") tests ;;
    "imod.time") gettime ;;
    "imod.rest") rest_request ;;
    *) exit 1 ;;
    esac
}

# check this script is excuted with source or not
_is_sourced() {
    # https://unix.stackexchange.com/a/215279
    local executed_script=$(basename ${0#-})
    local this_script=$(basename ${BASH_SOURCE})
    if [[ ${executed_script} = ${this_script} ]]; then
        # echo "this script is executed independently"
        return 1
    fi

    # echo "this script is sourced by executed_script"
    return 0
}

if ! _is_sourced; then
    # print args
    print_args "$@"

    # pass positional param from $1 to $N
    _main "$@"
fi

exit 0
