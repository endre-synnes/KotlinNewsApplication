#!/bin/bash

cd git-reopo

ls -ltr

echo "Using MAVEN_OPTS: ${MAVEN_OPTS}"

mvn -f git-repo/pom.xml install