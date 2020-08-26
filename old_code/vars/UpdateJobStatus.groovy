#!/usr/bin/env groovy

import com.mariadb.es.Build

def call(status) {
  if(status != 'SUCCESS') {
    currentBuild.result = status
  }
}
