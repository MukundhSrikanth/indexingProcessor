#!/bin/bash
if [ -z "$1" ]; then
  echo "Please provide the input file as an argument"
  exit 1
fi

# Get absolute path to file
FILE_PATH=$(realpath "$1")
DIR_PATH=$(dirname "$FILE_PATH")
FILE_NAME=$(basename "$FILE_PATH")

docker run --rm -v "$DIR_PATH":/input index-processor /input/"$FILE_NAME"
