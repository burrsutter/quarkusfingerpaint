#!/bin/bash

# use docker images | grep quarkusfingerpaint to get the image ID for $1

docker login quay.io

docker tag $1 quay.io/burrsutter/quarkusfingerpaint:1.0.0

docker push quay.io/burrsutter/quarkusfingerpaint:1.0.0