#!/usr/bin/env groovy

def call() {
  cleanWs()
  checkout scm
  currentCommit = GIT_COMMIT
  currentBranch = GIT_BRANCH
  currentBuild.displayName = "#${BUILD_NUMBER}: ${GIT_BRANCH}"
  currentBuild.description = "Revision: ${GIT_COMMIT}"
  currentVersion = readProperties file: 'VERSION'
  shortVersion = "${env.MYSQL_VERSION_MAJOR}.${env.MYSQL_VERSION_MINOR}"
  fullFersion = "${shortVersion}.${env.MYSQL_VERSION_PATCH}${env.MYSQL_VERSION_EXTRA}"
  serverMaturity = "${env.SERVER_MATURITY}"
}
