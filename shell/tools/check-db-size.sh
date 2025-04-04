#!/bin/bash 

##############
# VAR COMMON
##############
SCRIPT_DIR=$(dirname $(readlink -e "$0"))    # $(cd $(dirname "$0") && pwd -P)
SCRIPT_FILE=$(basename $(test -L $0 && readlink $0 || echo $0))    # "${0##*/}"


##############
# LOGS
##############
# syntax) echo_log "my messages"
: ${LOG_FILE:=/dev/null}
R='\033[0;31m'  # RED
G='\033[0;32m'  # GREEN
B='\033[0;34m'
N='\033[0m'     # NORMAL (NO COLOR)


# if stdout is tty or pseudo-tty then return 0 (true)
isatty() {
  if [[ -t 1 ]]; then
    return 0    # true
  else
    return 1    # false
  fi
}

# syntax) echo_log "my messages"
# syntax) var=$(echo; ls /); echo_log "$var"
echo_log() {
  echo -e "$(date --rfc-3339=seconds) $1" | tee -a ${LOG_FILE}
}

echo_success() {
  if isatty; then
    echo_log "${G}[SUCCESS]${N} $1"
  else
    echo_log "[SUCCESS] $1"
  fi
}

echo_failed() {
  if isatty; then
    echo_log "${R}[FAILED]${N} $1"
  else
    echo_log "[FAILED] $1"
  fi
}

echo_fatal() {
  if isatty; then
    echo_log "${R}[FATAL]${N} $1"
  else
    echo_log "[FATAL] $1"
  fi
  exit 255
}


##############
# FUNC COMMON
##############
# root checking
check_root_euid() {
  if [[ ${EUID} -ne 0 ]]; then
    echo_failed "${FUNCNAME}: Please run this script as root"
    exit 1
  fi
}



#######################
# SCRIPT :: START
#######################

#######################
## VARS
#######################
: ${CONTAINER_POSTGRES:="postgres"}
: ${POSTGRES_IP:="127.0.0.1"}
: ${POSTGRES_PORT:="5432"}
: ${POSTGRES_DBNAME:="postgres"}
: ${POSTGRES_USER:="postgres"}
: ${POSTGRES_PASSWD:="pgpasswd}"

: ${CONTAINER_MONGO:="mongos"}
: ${MONGOS_IP:="127.0.0.1"}
: ${MONGOS_PORT:="27017"}
: ${MONGO_DBNAME:="admin"}
: ${MONGO_USER:="admin"}
: ${MONGO_PASSWD:="admin"}


#######################
## FUNC
#######################
is_postgres_available() {
  local retry_count=0
  local max_retry=$2

  echo_log "waiting for postgres boot complete"
  while :; do

    if [[ ${retry_count} -gt ${max_retry} ]]; then
      echo_log "CRITICAL: failed to connect postgres database. retry count reached max"
      echo_failed ${FUNCNAME}
      exit 1
    fi

    ret=$(PGPASSWORD=${db_password} psql -h ${db_server} -p ${db_port} -U ${db_user} -d ${db_database} -c "select 'alive'" &> /dev/null;)
    if [[ $? -eq 0 ]]; then
      echo_success ${FUNCNAME}
      return
    fi
    
    echo_log "[${retry_count}] waiting for db ready to access..."
    ((retry_count++))
    sleep 2
  done
}


is_mongodb_available() {
  echo_log "[start] ${FUNCNAME} $@"

  local check_port=$1
  local retry_count=0
  local max_retry=$2

  if [ -z ${max_retry} ]; then
    local max_retry=10
  fi
  echo_log "MongoDB(${check_port}) connection test"

  while :; do
    if [[ ${retry_count} -gt ${max_retry} ]]; then
      echo_log "max retries exceeded."
      echo_log "failed port: ${check_port}"
      echo_failed ${FUNCNAME}
      exit 1
    fi

    mongosh 127.0.0.1:${check_port}/admin --eval "db.stats({freeStorage:1})"
    #mongosh 127.0.0.1:${check_port}/admin --eval "db.runCommand({dbStats: 1, freeStorage: 1})"
    if [[ $? -eq 0 ]]; then
      echo_success ${FUNCNAME}
      return
    fi

    echo_log "[${retry_count}] waiting for db ready to access..."
    ((retry_count++))
    sleep 2
  done
}


#######################
## MAIN
#######################
echo_log ""
echo_log "###################################"
echo_log "# HOST MEM"
echo_log "###################################"
ret=$(echo; free)
echo_log "$ret"

if [[ "$(docker container inspect -f '{{.State.Status}}' ${CONTAINER_POSTGRES})" = "running" ]]; then
  echo_log ""
  echo_log "###################################"
  echo_log "# POSTGRES"
  echo_log "###################################"
  ret=$(echo; docker exec ${CONTAINER_POSTGRES} psql -h ${POSTGRES_IP} -p ${POSTGRES_USER} -d ${POSTGRES_DBNAME} -c 'SELECT pg_database.datname,pg_size_pretty(pg_database_size(pg_database.datname)) AS size FROM pg_database')
  echo_log "$ret"
fi

if [[ "$(docker container inspect -f '{{.State.Status}}' ${CONTAINER_MONGO})" = "running" ]]; then
  echo_log ""
  echo_log "###################################"
  echo_log "# MONGODB : eppnosql  (kB)"
  echo_log "###################################"
  ret=$(echo; docker exec ${CONTAINER_MONGO} \
        mongosh ${MONGOS_IP}:${MONGOS_PORT}/${MONGO_DBNAME} -u ${MONGO_USER} -p ${MONGO_PASSWD} \
        --quiet --json=relaxed --eval "db.runCommand( { dbStats: 1, scale: 1024, freeStorage: 1 } ).raw")
  echo_log "$ret"
fi

echo_log ""
echo_log "###################################"
echo_log "# HOST DISK  (kB)"
echo_log "###################################"
ret=$(echo; df)
echo_log "$ret"


echo_log ""
echo_log "###################################"
echo_log "# HOST /opt  (kB)"
echo_log "###################################"
ret=$(echo; du /opt | sort -hr)
echo_log "$ret"

