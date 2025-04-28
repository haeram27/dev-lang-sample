#!/usr/bin/env bash
TESTNAME=$1
: ${TESTNAME:="HelloTests.hello"}
gradle test --rerun-tasks --tests "${TESTNAME}"
