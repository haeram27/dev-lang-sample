#!/bin/bash

hello() {
    echo "Hello, world!"
}

bye() {
    echo "Goodbye!"
}

# get function name by 1st arg($1) and run
if declare -f "$1" > /dev/null; then
    "$1"   # run function name of $1
else
    echo "Error: function '$1' not found"
    exit 1
fi

## how to run:
# run-func-with-name-of-arg.sh "hello"
