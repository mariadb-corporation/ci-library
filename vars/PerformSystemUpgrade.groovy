#!/usr/bin/env groovy

def call(label) {
  assert label != null

  echo "Trying to upgrade ${label} platform..."

  switch (label) {

    case "sles-12":
    case "sles-15":
      retry(10) { sh "sudo zypper -n update" }
      break

    case "rhel-6":
    case "rhel-7":
    case "rhel-8":
      sh "sudo yum -y clean all"
      retry(5) { sh "sudo yum -y upgrade" }
      break

    case "debian-9":
    case "debian-10":
    case "ubuntu-1604":
    case "ubuntu-1804":
    case "ubuntu-2004":
      retry(5) {
        sh "until sudo apt-get update; do sleep 10; done"
        sh "until sudo apt-get -y upgrade; do sleep 10; done"
        sh "sudo apt-get -y autoremove"
      }
      break

    default:
      error "I dont know how to upgrade ${label} platform!"
      break
  }


}
