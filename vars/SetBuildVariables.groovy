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

  def changeLogSets = currentBuild.changeSets
<<<<<<< HEAD
  def entries = changeLogSets[changeLogSets.size() - 1].items
  def entry = entries[entries.length - 1]

||||||| parent of f824c3a... fixup! added variables for Slack notification
  def entries = changeLogSets[changeLogSets.size()].items
  def entry = entries[entries.length]
=======
  def entries = changeLogSets[changeLogSets.size() - 1].items
<<<<<<< HEAD
  def entry = entries[entries.length]
>>>>>>> f824c3a... fixup! added variables for Slack notification
||||||| parent of 853d0d2... fixup! added variables for Slack notification
  def entry = entries[entries.length]
=======
  def entry = entries[entries.length - 1]
>>>>>>> 853d0d2... fixup! added variables for Slack notification
  echo "${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)}: ${entry.msg}"

  def files = new ArrayList(entry.affectedFiles)

  for (int k = 0; k < files.size(); k++) {
    def file = files[k]
      echo "  ${file.editType.name} ${file.path}"
  }
}
