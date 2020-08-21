#!/usr/bin/env groovy

import com.mariadb.es.Build

def call() {

  cleanWs()
  checkout scm

  Build.currentCommit = GIT_COMMIT
  Build.currentBranch = GIT_BRANCH
  Build.currentVersion = readProperties file: 'VERSION'
  Build.minorVersion   = Build.currentVersion.MYSQL_VERSION_MINOR
  Build.shortVersion   = Build.currentVersion.MYSQL_VERSION_MAJOR + '.' + Build.currentVersion.MYSQL_VERSION_MINOR
  Build.fullVersion    = Build.shortVersion + '.' + Build.currentVersion.MYSQL_VERSION_PATCH + Build.currentVersion.MYSQL_VERSION_EXTRA
  Build.serverMaturity = Build.currentVersion.SERVER_MATURITY

}
