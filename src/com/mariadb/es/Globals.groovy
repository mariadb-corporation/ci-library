package com.mariadb.es

class Globals {
  static currentCommit
  static currentBranch
  static currentVersion
  static minorVersion
  static shortVersion
  static fullVersion
  static serverMaturity
  static cmakeParams = '-DBUILD_CONFIG=enterprise'
  static defaultMtrParams = "--force --vardir=${mysqlVardir}"
  final static  mysqlVardir = '/var/tmp/mtr'
}
