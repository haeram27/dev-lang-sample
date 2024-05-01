#!/usr/bin/env bash
TEST_NAME=$1
: ${TEST_NAME:="HelloTests.hello"}
gradle test --rerun-tasks --tests "${TEST_NAME}"
