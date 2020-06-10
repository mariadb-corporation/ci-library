#!/usr/bin/env groovy

def call(galera=false) {
  def outdir = "bintar/${PLATFORM}"
  def defaultMtrParams = [ '--force', '--verbose-restart' ]
  defaultMtrParams << "--vardir=${mysqlVardir}"

  def parallel = 'auto'
  if (galera == true) {
    parallel = ${env.NCPU}.toInteger() / 2
  }
  defaultMtrParams << "--parallel=${parallel}"



  cleanWs()

  copyArtifacts filter: "${outdir}/*.tar.gz",
    fingerprintArtifacts: true,
    flatten: true,
    projectName: '${JOB_NAME}',
    selector: specific('${BUILD_NUMBER}')

}
