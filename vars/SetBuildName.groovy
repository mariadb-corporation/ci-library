#!/usr/bin/env groovy

import com.mariadb.es.Build

def call() {
  currentBuild.displayName = "#${BUILD_NUMBER}: ${Build.currentBranch}"
  currentBuild.description = "Revision: ${Build.currentCommit}"
}
