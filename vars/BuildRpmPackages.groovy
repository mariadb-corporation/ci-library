#!/usr/bin/env groovy

def call(cmakeParams) {
  assert cmakeParams != null
  def builddir ="padding_for_CPACK_RPM_BUILD_SOURCE_DIRS_PREFIX_ON_ES_BACKUP_DEBUGSOURCE"
  def outdir = "rpms/${PLATFORM}"
  def rpm = "${PLATFORM}".replaceAll("-", "")
  cleanWs()

  copyArtifacts filter: 'sourcetar/*.tar.gz',
    fingerprintArtifacts: true,
    flatten: true,
    projectName: '${JOB_NAME}',
    selector: specific('${BUILD_NUMBER}')

  sh "tar xzf mariadb-*.tar.gz --strip-components=1 -C ${builddir}"
  sh 'rm -vf mariadb-*.tar.gz'
  sh "mkdir -p ${outdir}"
  sh """
      [[ -f /opt/rh/devtoolset-3/enable ]] && source /opt/rh/devtoolset-3/enable

      cd ${builddir}

      if [[ -d storage/columnstore/columnstore ]] && [[ "${PLATFORM}" != 'rhel-6' ]]; then
        columnStore="-DPLUGIN_COLUMNSTORE=YES"
      fi

      if [[ ${PLATFORM} = rhel-6 ]]; then
        curl -o ../MariaDB-shared-5.3.x.rpm  \
          http://yum.mariadb.org/5.3/centos5-amd64/rpms/MariaDB-shared-5.3.12-122.el5.x86_64.rpm
        curl -o ../MariaDB-shared-10.1.x.rpm \
          http://yum.mariadb.org/10.1/centos6-amd64/rpms/MariaDB-shared-10.1.45-1.el6.x86_64.rpm
      fi

      if [[ ${PLATFORM} = rhel-7 ]]; then
        curl -o ../MariaDB-shared-5.3.x.rpm  \
          http://yum.mariadb.org/5.3/centos5-amd64/rpms/MariaDB-shared-5.3.12-122.el5.x86_64.rpm
        curl -o ../MariaDB-shared-10.1.x.rpm \
          http://yum.mariadb.org/10.1/centos7-amd64/rpms/MariaDB-shared-10.1.45-1.el7.centos.x86_64.rpm
      fi

      if [[ ${PLATFORM} = sles-12 ]] || [[ ${label} = sles-15 ]]; then
        curl -o ../MariaDB-shared-5.3.x.rpm  \
          http://yum.mariadb.org/5.3/centos5-amd64/rpms/MariaDB-shared-5.3.12-122.el5.x86_64.rpm
        curl -o ../MariaDB-shared-10.1.x.rpm \
          http://yum.mariadb.org/10.1/sles12-amd64/rpms/MariaDB-shared-10.1.45-1.x86_64.rpm
      fi

      cmake . ${cmakeParams} -DRPM=${rpm} \${columnStore:-} 2>&1 | tee ${WORKSPACE}/${outdir}/${PLATFORM}-cmake.log
      make -j${env.NCPU} package 2>&1 | tee ${WORKSPACE}/${outdir}/${PLATFORM}-make.log
      rm -fv ../MariaDB-shared-*.rpm
    """
  sh "mv -fv ${builddir}/*.rpm ${outdir}"
  archiveArtifacts "${outdir}/*"
  recordIssues id: "${PLATFORM}", name: "${PLATFORM}", aggregatingResults: true, enabledForFailure: true, tools: [cmake(), gcc()]
}
