#!/bin/bash
set -e
echo "Building and running product-service, brand-service, notification-service..."
docker-compose build product-service brand-service notification-service
docker-compose up -d product-service brand-service notification-service
