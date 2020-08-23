package com.mariadb.es

class Build {
  static currentCommit
  static currentBranch
  static commiterName
  static commiterEmail
  static currentVersion
  static minorVersion
  static shortVersion
  static fullVersion
  static serverMaturity
  static cmakeParams = '-DBUILD_CONFIG=enterprise'
  static defaultMtrParams = "--force --vardir=${mysqlVardir}"
  final static mysqlVardir = '/var/tmp/mtr'
  final static buildsToKeep = '100'
  final static slackChannel = '#build-status'
}
