#!/usr/bin/env groovy

import com.mariadb.es.Globals


def call() {

  cleanWs()
  checkout scm

  currentBuild.displayName = "#${BUILD_NUMBER}: ${GIT_BRANCH}"
  currentBuild.description = "Revision: ${GIT_COMMIT}"

  Globals.currentCommit = GIT_COMMIT
  Globals.currentBranch = GIT_BRANCH
  Globals.currentVersion = readProperties file: 'VERSION'
  Globals.minorVersion   = Globals.currentVersion.MYSQL_VERSION_MINOR
  Globals.shortVersion   = Globals.currentVersion.MYSQL_VERSION_MAJOR + '.' + Globals.currentVersion.MYSQL_VERSION_MINOR
  Globals.fullVersion    = Globals.shortVersion + '.' + Globals.currentVersion.MYSQL_VERSION_PATCH + Globals.currentVersion.MYSQL_VERSION_EXTRA
  Globals.serverMaturity = Globals.currentVersion.SERVER_MATURITY
}
