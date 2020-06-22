#!/usr/bin/env groovy

def call() {

  cleanWs()
  checkout scm

  currentCommit = GIT_COMMIT
  currentBranch = GIT_BRANCH
  currentBuild.displayName = "#${BUILD_NUMBER}: ${GIT_BRANCH}"
  currentBuild.description = "Revision: ${GIT_COMMIT}"

  def currentVersion = readProperties file: 'VERSION'
  echo currentVersion

  shortVersion = "${currentVersion.MYSQL_VERSION_MAJOR}.${currentVersion.MYSQL_VERSION_MINOR}"
  fullVersion = "${shortVersion}.${currentVersion.MYSQL_VERSION_PATCH}${currentVersion.MYSQL_VERSION_EXTRA}"
  serverMaturity = "${currentVersion.SERVER_MATURITY}"

  assert currentCommit  != null
  assert currentBranch  != null
  assert currentVersion != null

  assert shortVersion   != null
  assert fullVersion    != null
  assert serverMaturity != null

}
