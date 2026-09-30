#!/bin/sh
# ---------------------------------------------------------------------------
# Maven Wrapper (variantă simplificată, pentru Linux și macOS)
#
# Descarcă o singură dată versiunea de Maven indicată în
# .mvn/wrapper/maven-wrapper.properties, o păstrează în ~/.m2/wrapper/dists
# și rulează comanda cerută, de exemplu:   ./mvnw spring-boot:run
# ---------------------------------------------------------------------------
set -e

BASEDIR=$(cd "$(dirname "$0")" && pwd)
PROPS="$BASEDIR/.mvn/wrapper/maven-wrapper.properties"
URL=$(sed -n 's/^distributionUrl=//p' "$PROPS" | tr -d '\r')
NAME=$(basename "$URL" -bin.zip)
DIST="${MAVEN_USER_HOME:-$HOME/.m2}/wrapper/dists/$NAME"

if [ ! -x "$DIST/bin/mvn" ]; then
  echo "Descarc $NAME din $URL ..."
  TMP=$(mktemp -d)
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL -o "$TMP/maven.zip" "$URL"
  else
    wget -q -O "$TMP/maven.zip" "$URL"
  fi
  unzip -q "$TMP/maven.zip" -d "$TMP"
  mkdir -p "$(dirname "$DIST")"
  mv "$TMP/$NAME" "$DIST"
  rm -rf "$TMP"
fi

MAVEN_PROJECTBASEDIR="$BASEDIR"
export MAVEN_PROJECTBASEDIR
cd "$BASEDIR"
exec "$DIST/bin/mvn" "$@"
