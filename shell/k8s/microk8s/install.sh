#!/usr/bin bash

check_root_euid() {
    if [[ ${EUID} -ne 0 ]]; then
        echo_failed "${FUNCNAME}: Please run this script as root"
        exit 1
    fi
}

check_root_euid

# install micro-k8s
sudo snap install microk8s --classic
sudo usermod -a -G microk8s ${USER}
sudo chown -R ${USER} ~/.kube
newgrp microk8s
microk8s status --wait-ready

# kubectl configuration
#microk8s config > ~/.kube/config

# set limit of max user watches
FILE_PATH="/etc/sysctl.conf"
PATTERN="^fs\.inotify\.max_user_watches"
if grep -qE "$PATTERN" "$FILE_PATH"; then
    sed -i "/$PATTERN/d" "$FILE_PATH"; then
    echo 'fs.inotify.max_user_watches=1048576' | sudo tee -a /etc/sysctl.conf
    sudo sysctl --system
fi


microk8s inspect
