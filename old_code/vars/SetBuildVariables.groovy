#!/usr/bin/env groovy

import com.mariadb.es.Build

def call() {

// TODO uncomment
//  cleanWs()
  checkout scm

  Build.currentCommit = GIT_COMMIT
  Build.currentBranch = GIT_BRANCH
  Build.currentVersion = readProperties file: 'VERSION'
  Build.minorVersion   = Build.currentVersion.MYSQL_VERSION_MINOR
  Build.shortVersion   = Build.currentVersion.MYSQL_VERSION_MAJOR + '.' + Build.currentVersion.MYSQL_VERSION_MINOR
  Build.fullVersion    = Build.shortVersion + '.' + Build.currentVersion.MYSQL_VERSION_PATCH + Build.currentVersion.MYSQL_VERSION_EXTRA
  Build.serverMaturity = Build.currentVersion.SERVER_MATURITY

  File propfile = new File(Build.buildinfo)
  propfile.write("GIT_COMMIT=${Build.currentCommit}")


  def changeLogSets = currentBuild.changeSets
  if(changeLogSets.size() <= 0){
    Build.changedFiles = 0
    Build.changedFilesList = "No changes"
    return
  }
  def commiters = []
  for (int i = 0; i < changeLogSets.size(); i++) {
    def entries = changeLogSets[i].items
    for (int j = 0; j < entries.length; j++) {
        def entry = entries[j]
        def files = new ArrayList(entry.affectedFiles)
        Build.changedFilesList += "${entry.author}:\n\tCommit message:\n${entry.msg}\n\n"
        commiters.add(entry.author.toString())
        Build.changedFiles = files.size()
        for (int k = 0; k < files.size(); k++) {
            def file = files[k]
            Build.changedFilesList += "\t${file.editType.name} ${file.path}\n"
        }
        Build.changedFilesList += "\n"
    }
  }
  Build.commiterNames = commiters.unique().join(', ')
}
