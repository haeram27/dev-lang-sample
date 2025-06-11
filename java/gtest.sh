#!/usr/bin/env bash
: ${TESTNAME:="HelloTests.hello"}
gradle test --rerun-tasks --tests "${TESTNAME}"