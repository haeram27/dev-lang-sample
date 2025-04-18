#!/bin/bash 

##############
# VARS
##############
IMODE="imod.test"
VMODE_TIMEUTC="no"


##############
# VAR COMMON
##############
SCRIPT_DIR=$(dirname $(readlink -e "$0"))    # $(cd $(dirname "$0") && pwd -P)
SCRIPT_FILE=$(basename $(test -L $0 && readlink $0 || echo $0))    # "${0##*/}"

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
R='\033[0;31m'  # RED
G='\033[0;32m'  # GREEN
B='\033[0;34m'
N='\033[0m'     # NORMAL (NO COLOR)


# if stdout is tty or pseudo-tty then return 0 (true)
isatty() {
  if [[ -t 1 ]]; then
    return 0    # true
  else
    return 1    # false
  fi
}

# syntax) echo_log "my messages"
# syntax) var=$(echo; ls /); echo_log "$var"
echo_log() {
  echo -e "$(date --rfc-3339=seconds) $1" | tee -a ${LOG_FILE}
}

echo_success() {
  if isatty; then
    echo_log "${G}[SUCCESS]${N} $1"
  else
    echo_log "[SUCCESS] $1"
  fi
}

echo_failed() {
  if isatty; then
    echo_log "${R}[FAILED]${N} $1"
  else
    echo_log "[FAILED] $1"
  fi
}

echo_fatal() {
  if isatty; then
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
  [[ $(true &>/dev/null; echo $?) -eq 0 ]] && echo success || echo failed
}


print_args() {
  local idx=1
  local i
  for i do
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

  read -p "Do you want print Hello World? Are you sure? (y/n) " -n 1;
  echo ""
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
    while :; do date -u --rfc-3339=ns; sleep 0.1; done;
  else
    echo 'LOCAL::'
    while :; do date --rfc-3339=ns; sleep 0.1; done;
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
git-repo-push () {
        if [ -n "${GIT_REPO_PATH}" ]
        then
                echo "GIT_REPO_PATH=${GIT_REPO_PATH}"
                pushd ${GIT_REPO_PATH} &> /dev/null
                if git st &> /dev/null
                then
                        git add .
                        git cmm "$(date -u --rfc-3339=ns)"
                        git ps
                else
                        echo "GIT_REPO_PATH is NOT git repository"
                fi
                popd &> /dev/null
        else
                echo "GIT_REPO_PATH is empty"
        fi
}

git-repo-pull () {
        if [ -n "${GIT_REPO_PATH}" ]
        then
                echo "GIT_REPO_PATH=${GIT_REPO_PATH}"
                pushd ${GIT_REPO_PATH} &> /dev/null
                if git st &> /dev/null
                then
                        git pull
                else
                        echo "GIT_REPO_PATH is NOT git repository"
                fi
                popd &> /dev/null
        else
                echo "GIT_REPO_PATH is empty"
        fi
}


##############
# http handling
##############
rest_request() {
  echo_log ${FUNCNAME} "$@"

  RESP_FILE=\/tmp\/$(uuidgen | tr -d '-')$(date -u +%Y%m%d%H%M%S%N)
  REQ_TIMEOUT=2
  if [[ -z $1 ]]; then
    HTTP_URL='http://www.google.com'
  else
    HTTP_URL=$1
  fi
   
  HTTP_CODE=$(curl -fksSL --connect-timeout ${REQ_TIMEOUT} -w "%{response_code}" -o ${RESP_FILE} ${HTTP_URL})
  CURL_RET=$?
  if [[ ${CURL_RET} -eq 0 ]]; then
    if [[ "${HTTP_CODE}" == "200" ]]; then
      ## do something with ${RESP_FILE}
      echo ${RESP_FILE}; cat ${RESP_FILE};
    else
      echo warning: httpcode is ${HTTP_CODE} >&2
    fi
  else
    echo error: curl returns ${CURL_RET} >&2
  fi

  [[ -f ${RESP_FILE} ]] && (rm -f ${RESP_FILE} &>/dev/null)
}


##############
# file handling
##############
# remove lines has pattern in files
FILE_PATH="test.txt"
PATTERN="^fs\.inotify\.max_user_watches"
remove_pattern_line_in_files() {
  if grep -qE "${PATTERN}" "${FILE_PATH}"; then
    sed -i "/${PATTERN}/d" "${FILE_PATH}"; then
  fi
}


##############
# main
##############
# Usage: ./script.sh -h
_main() {
  # IMODE(instruction) option is to seclect start function 
  # VMODE(variable) option is to set flag variable of this script
  while getopts "thuq\?" opt
  do
    case ${opt} in
    h|\?) IMODE="imod.help" ;;
    t) IMODE="imod.time" ;;
    q) IMODE="imod.rest" ;;
    u) echo "turn on UTC mode"
        VMODE_TIMEUTC="yes" ;;
    esac
  done
   
  #echo "IMODE=${IMODE}"
  case $IMODE in
    "imod.help") help ;;
    "imod.test") tests ;;
    "imod.time") gettime ;;
    "imod.rest") rest_request;;
    *) exit 1 ;;
  esac
}

# check this script is excuted with source or not
_is_sourced() {
  # https://unix.stackexchange.com/a/215279
  local executed_script=$( basename ${0#-} )
  local this_script=$( basename ${BASH_SOURCE} )
  if [[ ${executed_script} = ${this_script} ]] ; then
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
