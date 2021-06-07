#!/usr/bin/env groovy

import com.mariadb.es.Build

def call(branch, commit) {
  currentBuild.displayName = "#${BUILD_NUMBER}: ${branch}"
  currentBuild.description = "Revision: ${commit}"
}
