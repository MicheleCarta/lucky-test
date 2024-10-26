#!/bin/sh
set -e

: ${VERSION:="latest"}
export VERSION

./gradlew

docker build --build-arg VERSION=${VERSION} -t production/user-service:${VERSION} .
