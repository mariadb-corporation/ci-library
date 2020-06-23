#!/usr/bin/env groovy

def call() {

  cleanWs()
  checkout scm

  currentCommit = GIT_COMMIT
  currentBranch = GIT_BRANCH
  currentBuild.displayName = "#${BUILD_NUMBER}: ${GIT_BRANCH}"
  currentBuild.description = "Revision: ${GIT_COMMIT}"

}
