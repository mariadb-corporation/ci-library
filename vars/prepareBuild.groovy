#!/usr/bin/env groovy

/*
This custom step is intended to do all the required actions before build starts
*/

def call() {

  cleanWs()
  checkout scm
  setBuildName()
//  def causes = currentBuild.getBuildCauses()
//  echo "${causes}"


}
