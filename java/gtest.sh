#!/usr/bin/env bash
: ${TEST_NAME:=$1}
gradle test --rerun-tasks --tests "${TEST_NAME}"
