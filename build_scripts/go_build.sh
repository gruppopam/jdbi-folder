#!/bin/bash

set -e

mvn -U clean deploy -DskipTests
 