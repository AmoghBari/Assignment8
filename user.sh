#!/bin/bash

# Sleep for a while to ensure that the instance is fully initialized
sleep 60

# Install and start Nginx
    sudo yum update -y
    sudo yum install -y nginx
    sudo service nginx start
    sudo chkconfig nginx on
