#!/usr/bin/env bash
# syntax) echo_log "my messages"
# syntax) echo_log "my messages"
: ${LOG_FILE:=/dev/null}
R='\033[0;31m'  # RED
G='\033[0;32m'  # GREEN
N='\033[0m'     # NORMAL (NO COLOR)

is_tty() {
  if [[ -t 1 ]]; then
    return 0
  else
    return 1
  fi
}

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

echo_fatal() {
  if is_tty; then
    echo_log "${R}[FATAL]${N} $1"
  else
    echo_log "[FATAL] $1"
  fi
  exit 255
}


# Usage: run_container <continer>
run_container() {
  echo_log "[start] ${FUNCNAME} $@"\

  local container_name=$1
  local retry_count=0
  local max_retry=30
  local cont_last_stat="unknown"

  if [[ -z ${container_name} ]]; then
    echo "FATAL: empty container name"
    exit 1;
  fi

  while [[ "$(docker container inspect -f '{{.State.Status}}' ${container_name})" != "running" ]]; do
    if [[ ${retry_count} -gt ${max_retry} ]]; then
      echo "can NOT start ${container_name} container"
      exit 1;
    fi

    docker start ${container_name} &>/dev/null

    ((retry_count++))
    sleep 3;
  done;
}

# Usage: container_force_shutdown <container>
container_force_shutdown() {
  echo_log "[start] ${FUNCNAME} $@"

  local container_name=$1
  local timeout=60


  if [[ -z ${container_name} ]]; then
    echo_fatal "empty container name"
    exit 1;
  fi

  docker stop -t ${timeout} ${container_name} &>/dev/null
}

# Usage: container_graceful_shutdown <container> <func-to-terminate-process>
container_graceful_shutdown() {
  echo_log "[start] ${FUNCNAME} $@"

  local container_name=$1
  local terminate_process_function=$2
  local retry_count=0
  local max_retry=60

  if [[ -z ${container_name} ]]; then
    echo_fatal "empty container name"
    exit 1;
  fi

  if [[ -z ${terminate_process_function} ]]; then
    echo_fatal "empty container name"
    exit 1;
  fi

  if [[ "$(docker container inspect -f '{{.State.Status}}' ${container_name})" == "running" ]]; then
    echo_log "try to stop container by itself: [${container_name}]"
    while :; do
      if [[ ${retry_count} -gt ${max_retry} ]]; then
        echo_log "max retries exceeded for graceful shutdown."
        echo_log "forced shutdown is progressing."
        docker kill ${container_name}
        exit 1
      fi
      if [[ $((retry_count%4)) -eq 0 ]]; then
        "terminate_process_function"
      fi
      docker container inspect ${container_name} &>/dev/null
      if [[ $? -ne 0 ]]; then
        echo_success "stop container ${container_name}"
        break
      fi
      echo_log "[${retry_count}] waiting to stop container: ${container_name}"
      ((retry_count++))
      sleep 3
    done
  fi
}

# Usage: is_container_running mycontainer || (echo "mycontainer is not running" && exit 1)
# available status: "created", "running", "restarting" "paused" "exited"
is_container_running() {
  echo_log "[start] ${FUNCNAME} $@"

  local name=$1

  if [[ -z ${name} ]]; then
    echo_fatal "INVALID argument"
  fi

  if [[ "$(docker container inspect -f '{{.State.Status}}' ${name})" != "running" ]]; then
    return 1
  fi

  return 0
}

