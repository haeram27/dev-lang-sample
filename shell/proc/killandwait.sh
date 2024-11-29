#!/usr/bin/env bash
pid=$1

if [[ ! -z ${pid} ]]; then
  kill -TERM ${pid}
  if [[ $? -eq 0 ]]; then
    while kill -0 ${pid} 2>/dev/null; do
      echo "Waiting for process ${pid} to exit..."
      sleep 2
    done
  fi
else
  echo "USAGE: $0 <pid>"
fi
