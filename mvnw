#!/bin/sh
# Apache Maven Wrapper (download-on-demand)
#
# Minimal wrapper script so contributors don't need Maven installed.

set -eu

BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
PROPS_FILE="$WRAPPER_DIR/maven-wrapper.properties"

if [ ! -f "$PROPS_FILE" ]; then
  echo "Missing $PROPS_FILE" >&2
  exit 1
fi

wrapperUrl=$(grep -E '^wrapperUrl=' "$PROPS_FILE" | cut -d= -f2- || true)
distributionUrl=$(grep -E '^distributionUrl=' "$PROPS_FILE" | cut -d= -f2- || true)

if [ -z "${wrapperUrl:-}" ] || [ -z "${distributionUrl:-}" ]; then
  echo "Invalid $PROPS_FILE (need wrapperUrl and distributionUrl)" >&2
  exit 1
fi

mkdir -p "$WRAPPER_DIR"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Downloading Maven Wrapper jar..." >&2
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$wrapperUrl" -o "$WRAPPER_JAR"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$wrapperUrl" -O "$WRAPPER_JAR"
  else
    echo "Need curl or wget to download Maven Wrapper jar." >&2
    exit 1
  fi
fi

exec java -Dmaven.multiModuleProjectDirectory="$BASE_DIR" \
  -classpath "$WRAPPER_JAR" \
  org.apache.maven.wrapper.MavenWrapperMain \
  -Dmaven.wrapper.distributionUrl="$distributionUrl" \
  "$@"

