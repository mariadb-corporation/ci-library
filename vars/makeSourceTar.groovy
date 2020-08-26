#!/usr/bin/env groovy
import com.mariadb.es.Config

def call() {

  def outdir = "sourcetar"

  sh "mkdir -p ${outdir}"
  sh "cmake . ${Config.cmakeParams} 2>&1 | tee ${outdir}/src-cmake.log"
  sh "make dist"
  sh "mv -fv *.tar.gz ${outdir}"

  archiveArtifacts artifacts: "${outdir}/*", fingerprint: true
}
