#!/usr/bin/env groovy

import com.mariadb.es.Globals

def call() {

  def outdir = "sourcetar"

  sh (script: "mkdir -p ${outdir}")
  sh (script: "cmake . ${Globals.cmakeParams} 2>&1 | tee ${outdir}/src-cmake.log")
  sh (script: "make dist")
  sh (script: "mv -fv *.tar.gz ${outdir}")
  archiveArtifacts "${outdir}/*"
}
