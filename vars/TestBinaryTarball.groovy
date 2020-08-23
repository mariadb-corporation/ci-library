#!/usr/bin/env groovy

import com.mariadb.es.Build

def call(name, params) {
  assert name != null
  assert params != null

  name = name.toLowerCase()

  def outdir = "bintar/${PLATFORM}"
  def xmlReport = "mtr-${name}.xml"
  def dataTarball = "mtr-${name}.tar.gz"
  def mtrLogfile = "mtr-${name}.log"
  def galeraLibraryName = 'libgalera_smm.so'
  def galeraLocation
  def myEnv = []

  def parallel = 'auto'

  cleanWs()

  copyArtifacts filter: "${outdir}/mariadb-*.tar.gz", fingerprintArtifacts: true,
    flatten: true, projectName: 'MariaDB-Enterprise-BINTAR', selector: specific("${BINTAR_BUILD_NUMBER}")

  sh 'tar xf mariadb-*.tar.gz --strip-components=1'
  sh 'rm -fv mariadb-*.tar.gz'

  File bindir = new File('bin')
  if(!bindir.exists()) {
    error "Tarball unpack error, directory bin/ does not exist!"
  }
  myEnv << "PATH=${WORKSPACE}/bin:${env.PATH}"

  if (name == 'galera') {
    parallel = "${env.NCPU}".toInteger() / 2
    if(Build.minorVersion.toInteger() > 3) {
      galeraLibraryName = 'libgalera_enterprise_smm.so'
    }
    galeraLocation = sh(script: "sudo find /usr -type f -name ${galeraLibraryName}", returnStdout: true).trim()
    if (galeraLocation == null) {
      error "WSREP PROVIDER library not found!"
    }
    myEnv << "WSREP_PROVIDER=${galeraLocation}"
  } // if (name == 'galera')

  String currentMtrParams = params + " --parallel=${parallel} --xml-report=${xmlReport}"

  echo "Running MTR with following parameters: ${currentMtrParams}..."
  sh "mkdir -p ${outdir}"

  withEnv(myEnv) {
    sh "cd mysql-test && perl mysql-test-run.pl ${currentMtrParams} 2>&1 | tee ../${outdir}/${mtrLogfile} ||:"
  }
  sh "tar czf ${outdir}/${dataTarball} ${Build.mysqlVardir}/*"
  sh "find mysql-test -name ${xmlReport} -exec cp -av '{}' ${outdir} \\;"
  junit healthScaleFactor: 2.0, testResults: "${outdir}/*.xml"
  archiveArtifacts "${outdir}/*"
}
