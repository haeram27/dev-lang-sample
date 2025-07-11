FROM rockylinux/rockylinux:9.5

COPY buildtemp/epel-el9-x64.repo /etc/yum.repos.d/


RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/*.repo && \
    sed -i 's/#baseurl=http:\/\/dl.rockylinux.org\/\$contentdir/baseurl=http:\/\/abis.ahnlab.com\/artifactory\/yum-remote-rockylinux-navercorp/g' /etc/yum.repos.d/*.repo

RUN dnf install -y dnf-plugins-core && dnf config-manager --enable devel && \
    dnf clean all && dnf update -y && \
#### build-tools
    dnf groupinstall -y "Development Tools" && \
    dnf install -y --allowerasing cmake meson ninja-build \
#### utils
        bzip2 chrony cronie git perl procps psmisc rsyslog strace sudo tree wget which \
#### common-dep
        jemalloc-devel pcre-devel unixODBC-devel dialog-devel libcurl-devel zlib-devel libxml2-devel libpcap-devel \
#### curl
        libpsl-devel \
        tcl \
#### END of packages
        zip


####--- clean up packages and systemsd
RUN dnf -y clean all && \
    (cd /lib/systemd/system/sysinit.target.wants; \
    for i in *; do [ $i == systemd-tmpfiles-setup.service ] || rm -f $i; done); \
    rm -f /lib/systemd/system/multi-user.target.wants/*;\
    rm -f /etc/systemd/system/*.wants/*;\
    rm -f /lib/systemd/system/local-fs.target.wants/*; \
    rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
    rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
    rm -f /lib/systemd/system/basic.target.wants/*;\
    rm -f /lib/systemd/system/anaconda.target.wants/*;
