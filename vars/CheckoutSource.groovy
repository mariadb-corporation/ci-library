#!/usr/bin/env groovy

def call() {

  cleanWs()
  checkout scm

/*
instead we can use shell routines to clean up the source and reset it
*/

  currentBuild.displayName = "#${BUILD_NUMBER}: ${GIT_BRANCH}"
  currentBuild.description = "Revision: ${GIT_COMMIT}"

}
