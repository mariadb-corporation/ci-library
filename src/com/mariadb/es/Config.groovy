package com.mariadb.es

class Config {
  final static buildsToKeep = '100'
  final static daysToKeep = '7'
  final static cmakeParams = '-DBUILD_CONFIG=enterprise'
  final static slackChannel = '#build-status'
  final static binJob = 'MariaDB-Enterprise-BINTAR'
  final static rpmJob = 'MariaDB-Enterprise-RPM'
  final static debJob = 'MariaDB-Enterprise-DEB'
  final static debBuildDir = 'MariaDBEnterprise'
  final static rpmBuildDir = 'padding_for_CPACK_RPM_BUILD_SOURCE_DIRS_PREFIX_ON_ES_BACKUP_DEBUGSOURCE'
}
