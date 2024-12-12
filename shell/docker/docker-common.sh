#!/usr/bin/env bash

# Usage: is_container_running mycontainer || (echo "mycontainer is not running" && exit 1)
is_container_running() {
  echo "[start] ${FUNCNAME} $@"

  local name=$1

  if [[ -z ${name} ]]; then
    echo_fatal "INVALID argument"
  fi

  if [[ "$(docker container inspect -f '{{.State.Status}}' ${name})" != "running" ]]; then
    return 1
  fi

  return 0
}

