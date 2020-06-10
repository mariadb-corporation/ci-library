#!/usr/bin/env groovy

def call(cmakeParams) {
  assert cmakeParams != null
  def outdir = "sourcetar"

  sh (script: "mkdir -p ${outdir}")
  sh (script: "cmake . ${cmakeParams} 2>&1 | tee ${outdir}/src-cmake.log")
  sh (script: "make dist")
  sh (script: "mv -fv *.tar.gz ${outdir}")
  archiveArtifacts "${outdir}/*"
}
