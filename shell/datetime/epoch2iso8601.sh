#!/bin/bash

epoch_nanosec=$1

epoch_sec=$(echo $epoch_nanosec | cut -b 1-10)
nano_sec=$(echo $epoch_nanosec | cut -b 11-)

date --date="@$epoch_sec" +"%Y-%m-%dT%H:%M:%S.$nano_sec%:z"
