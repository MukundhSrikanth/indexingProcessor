#!/bin/bash

if [ -z "$1" ]; then
  echo "Please provide the input file as an argument"
  exit 1
fi

# Use cygpath to convert to Windows path if running under Git Bash
if command -v cygpath >/dev/null 2>&1; then
  FILE_PATH=$(cygpath -w "$1")
else
  FILE_PATH="$1"
fi

DIR_PATH=$(dirname "$FILE_PATH")
FILE_NAME=$(basename "$FILE_PATH")

docker run --rm -v "$DIR_PATH":/input index-processor /input/"$FILE_NAME"
