#!/usr/bin/env groovy

import com.mariadb.es.Build

def call() {
  currentBuild.displayName = "#${BUILD_NUMBER}: ${GIT_BRANCH}"
  currentBuild.description = "Revision: ${GIT_COMMIT}"
}
