#!/usr/bin/env bash
set -euo pipefail
cd "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Prefer the fat jar built by the app module.
JAR=$(find app/build/libs -maxdepth 1 -type f -name '*-all.jar' | sort | tail -n 1)
if [[ -z "$JAR" ]]; then
  JAR=$(find app/build/libs -maxdepth 1 -type f -name '*.jar' | sort | tail -n 1)
fi

if [[ -z "$JAR" ]]; then
  echo "No jar found in app/build/libs. Run ./gradle :app:fatJar first." >&2
  exit 1
fi

JAVA_CMD=${JAVA_CMD:-java}
JAVA_VERSION_RAW=$($JAVA_CMD -version 2>&1 | awk -F '"' '/version/ {print $2; exit}')
if [[ -z "$JAVA_VERSION_RAW" ]]; then
  echo "Unable to determine Java version. Please check java -version output." >&2
  exit 1
fi

if [[ "$JAVA_VERSION_RAW" =~ ^1\.([0-9]+) ]]; then
  JAVA_MAJOR=${BASH_REMATCH[1]}
else
  JAVA_MAJOR=${JAVA_VERSION_RAW%%.*}
fi

if ! [[ "$JAVA_MAJOR" =~ ^[0-9]+$ ]]; then
  echo "Unknown Java version format: $JAVA_VERSION_RAW" >&2
  exit 1
fi

if (( JAVA_MAJOR <= 25 )); then
  echo "Java version must be 26 or higher. Current Java version: $JAVA_VERSION_RAW" >&2
  exit 1
fi

# Allow JVM options via JAVA_OPTS and application arguments via APP_OPTS.
# Example: JAVA_OPTS='-Xms64m -Xmx512m' APP_OPTS='--profile prod' ./run-fatjar.sh arg1 arg2
LOG_FILE=${LOG_FILE:-app.log}
COMMAND=($JAVA_CMD ${JAVA_OPTS:-} -jar "$JAR" ${APP_OPTS:-} "$@")

echo "Starting $JAR in background (nohup). Logs: $LOG_FILE"
nohup "${COMMAND[@]}" > "$LOG_FILE" 2>&1 &
PID=$!
echo "Started pid=$PID"
