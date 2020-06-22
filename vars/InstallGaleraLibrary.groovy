#!/usr/bin/env groovy

def call(label, minorVersion) {
assert label != null
assert minorVersion != null

def galeraVersion
def galeraPkgNames
def galeraUrl
File repoFile
def repoName = "Galera-Enterprise"

switch (minorVersion) {
  case 2:
  case 3:
    galeraVersion = 3
    debPkgNames = "galera-3 galera-arbitrator-3"
    rpmPkgNames = "galera"
    break
  case 4:
  case 5:
  case 6:
    galeraVersion = 4
    debPkgNames = "galera-enterprise-4 galera-arbitrator-4"
    rpmPkgNames = "galera-enterprise-4"
    break
  default:
    error "Unknown MariaDB minor version!"
    break
}

switch (label) {

  case "debian-9":
  case "debian-10":
  case "ubuntu-1604":
  case "ubuntu-1804":
  case "ubuntu-2004":
    galeraUrl = "http://repo/jenkins/DEVBUILDS/galera-${galeraVersion}/latest/apt ${label}/"
    repoFile = new File("galera.list")
    repoFile.write("deb [trusted=yes] ${galeraUrl}\n")
    println repoFile.text
    sh "sudo mv -vf galera.list /etc/apt/sources.list.d/"
    sh "sudo apt-get update"
    sh "sudo apt-get -y install ${debPkgNames}"
    break

  case "rhel-6":
  case "rhel-7":
  case "rhel-8":
    galeraUrl = "http://repo/jenkins/DEVBUILDS/galera-${galeraVersion}/latest/rpm/${label}/"
    repoFile = new File("galera.repo")
    repoFile.write("[${repoName}]\n")
    repoFile << "name=${repoName}\n"
    repoFile << "baseurl=${galeraUrl}\n"
    repoFile << "gpgcheck=0\n"
    repoFile << "enable=1\n"
    if(label == "rhel-8") {
      repoFile << "module_hotfixes=true\n"
    }
    sh "sudo yum install -y ${rpmPkgNames}"
    break

  case "sles-12":
  case "sles-15":
    galeraUrl = "http://repo/jenkins/DEVBUILDS/galera-${galeraVersion}/latest/rpm/${label}"
    sh "sudo zypper ar -f -C ${galeraUrl} ${repoName}"
    sh "sudo zypper --no-gpg-checks refresh ||:"
    sh "sudo zypper -n install --repo ${repoName} ${rpmPkgNames}"
    break

  default:
    error "Galera installation is not supported on ${label}!"
    break
}



}
