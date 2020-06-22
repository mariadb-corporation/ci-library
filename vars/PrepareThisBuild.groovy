#!/usr/bin/env groovy

def call() {

  cleanWs()
  checkout scm

  currentCommit = GIT_COMMIT
  currentBranch = GIT_BRANCH
  currentBuild.displayName = "#${BUILD_NUMBER}: ${GIT_BRANCH}"
  currentBuild.description = "Revision: ${GIT_COMMIT}"

  currentVersion = readProperties file: 'VERSION', interpolate: true
  minorVersion = currentVersion['MYSQL_VERSION_MINOR']
  shortVersion = currentVersion['MYSQL_VERSION_MAJOR'] + '.' + currentVersion['MYSQL_VERSION_MINOR']
  fullVersion = shortVersion + '.' + currentVersion['MYSQL_VERSION_PATCH'] + currentVersion['MYSQL_VERSION_EXTRA']
  serverMaturity = currentVersion['SERVER_MATURITY']

}
