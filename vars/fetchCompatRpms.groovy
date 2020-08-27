#!/usr/bin/env groovy

def call(label) {

  switch(label) {

    case "rhel-6":
      sh "curl -o MariaDB-shared-10.1.x.rpm http://yum.mariadb.org/10.1/centos6-amd64/rpms/MariaDB-shared-10.1.45-1.el6.x86_64.rpm"
      break

    case "rhel-7":
      sh "curl -o MariaDB-shared-10.1.x.rpm http://yum.mariadb.org/10.1/centos7-amd64/rpms/MariaDB-shared-10.1.45-1.el7.centos.x86_64.rpm"
      break

    case "sles-12":
    case "sles-15":
      sh "curl -o ../MariaDB-shared-10.1.x.rpm http://yum.mariadb.org/10.1/sles12-amd64/rpms/MariaDB-shared-10.1.45-1.x86_64.rpm"
      break

    default:
      echo "Not fetching compat RPMs for ${label}!"
      return
      break
  }
  sh "curl -o MariaDB-shared-5.3.x.rpm http://yum.mariadb.org/5.3/centos5-amd64/rpms/MariaDB-shared-5.3.12-122.el5.x86_64.rpm"
}
