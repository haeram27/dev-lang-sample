FROM rockylinux/rockylinux:9.5
ARG app_home
ARG app_user
ARG app_uid
ARG mongo_ver
ARG pgsql_ver

COPY tempspace/docker-ce-rhel.repo /etc/yum.repos.d/
COPY tempspace/mongodb-org-redhat-9.repo /etc/yum.repos.d/
COPY tempspace/pgdg-redhat-all.repo /etc/yum.repos.d/


RUN --mount=type=bind,source=tempspace,target=/tempspace \
    ls /tempspace && \
    mkdir -p ${app_home} && \
    groupadd -g ${app_uid} ${app_user} && \
    useradd -r -u ${app_uid} -d ${app_home} -g ${app_user} ${app_user} && \
    mkdir -p /database && \
    mkdir -p /nosql && \
    chown ${app_uid}:${app_uid} /nosql -R && \
    dnf clean all && \
    dnf install --allowerasing -y \
    procps \
    bzip2 \
    chrony \
    cronie \
    curl \
    rsyslog \
    strace \
    sudo \
    tree \
    wget \
    zip \
####--- redis
    redis \
####--- mongodb
    mongodb-org-${mongo_ver} mongodb-org-mongos-${mongo_ver} mongodb-org-server-${mongo_ver} mongodb-org-tools-${mongo_ver}

####--- postgres
RUN dnf -qy module disable postgresql && \
    dnf install --allowerasing -y \
    pgbouncer \
    postgresql-odbc \
    postgresql${pgsql_ver} postgresql${pgsql_ver}-contrib postgresql${pgsql_ver}-libs postgresql${pgsql_ver}-server

    ####--- clean up packages and systemsd
RUN dnf -y clean all && \
    chown postgres: /database -R && \
    (cd /lib/systemd/system/sysinit.target.wants/ && for i in *; do [ $i == systemd-tmpfiles-setup.service ] || rm -f $i; done) && \
    rm -f /lib/systemd/system/multi-user.target.wants/* && \
    rm -f /etc/systemd/system/*.wants/* && \
    rm -f /lib/systemd/system/local-fs.target.wants/* && \
    rm -f /lib/systemd/system/sockets.target.wants/*udev* && \
    rm -f /lib/systemd/system/sockets.target.wants/*initctl* && \
    rm -f /lib/systemd/system/basic.target.wants/* && \
    rm -f /lib/systemd/system/anaconda.target.wants/*

# set login-user at runtime
USER ${app_user}