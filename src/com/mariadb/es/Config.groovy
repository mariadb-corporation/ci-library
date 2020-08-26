package com.mariadb.es

class Config {
  final static buildsToKeep = '100'
  final static daysToKeep = '7'
  final static cmakeParams = '-DBUILD_CONFIG=enterprise'
  final static slackChannel = '#build-status'
}
