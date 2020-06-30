#!/usr/bin/env groovy

import com.mariadb.es.Build

def call() {

  def outdir = "bintar/${PLATFORM}"
  cleanWs()

  copyArtifacts filter: 'sourcetar/*.tar.gz',
    fingerprintArtifacts: true,
    flatten: true,
    projectName: '${JOB_NAME}',
    selector: specific('${BUILD_NUMBER}')
  sh 'tar xzf mariadb-*.tar.gz --strip-components=1'
  sh 'rm -vf mariadb-*.tar.gz'
  sh "mkdir -p ${outdir}"
  sh """
      [[ -f /opt/rh/devtoolset-3/enable ]] && source /opt/rh/devtoolset-3/enable
      cmake . ${Build.cmakeParams} 2>&1 | tee ${outdir}/${PLATFORM}-cmake.log
    """
  sh "make -j${env.NCPU} package 2>&1 | tee ${outdir}/${PLATFORM}-make.log"
  sh "mv -fv *.tar.gz ${outdir}"
  archiveArtifacts "${outdir}/*"
  recordIssues id: "${PLATFORM}", name: "${PLATFORM}", aggregatingResults: true, enabledForFailure: true, tools: [cmake(), gcc()]
}
