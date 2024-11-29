#!/usr/bin/env bash

set -evx

PID=0
: ${CMD:="nc -l 54321"}

terminate()
{
  if [ ${PID} -ne 0 ]; then
    kill -SIGTERM "${PID}"
    wait "${PID}"
  fi
  exit 143
}

sigusr1()
{
  if [ ${PID} -ne 0 ]; then
    kill -10 "${PID}"
    wait "${PID}"
  fi
}

trap 'terminate' SIGTERM
trap 'sigusr1' 10 # SIGUSR1

${CMD} &>/dev/null &  # background job

PID=$!
wait ${PID}
