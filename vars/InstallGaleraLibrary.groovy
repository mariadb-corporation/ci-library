#!/usr/bin/env groovy

def call(label, minorVersion) {
assert label != null
assert minorVersion != null

def galeraVersion
def galeraPkgNames
def galeraUrl
def repoName = "Galera-Enterprise"
def rhel8Fix = ""

switch (minorVersion) {
  case "2":
  case "3":
    galeraVersion = 3
    debPkgNames = "galera-3 galera-arbitrator-3"
    rpmPkgNames = "galera"
    break
  case "4":
  case "5":
  case "6":
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
    writeFile file: 'galera.list', text: "deb [trusted=yes] ${galeraUrl}"
    sh "cat galera.list"
    sh "sudo mv -vf galera.list /etc/apt/sources.list.d/"
    sh "sudo apt-get update"
    sh "sudo apt-get -y install ${debPkgNames}"
    break

  case "rhel-6":
  case "rhel-7":
  case "rhel-8":
    galeraUrl = "http://repo/jenkins/DEVBUILDS/galera-${galeraVersion}/latest/rpm/${label}/"
    if(label == "rhel-8") {
      rhel8Fix = "module_hotfixes=true"
    }
    writeFile file: 'galera.repo', text: "[${repoName}]\nname=${repoName}\nbaseurl=${galeraUrl}\ngpgcheck=0\nenable=1\n${rhel8Fix}\n"
    sh "cat galera.repo"
    sh "sudo mv -vf galera.repo /etc/yum.repos.d/"
    sh "sudo yum install -y ${rpmPkgNames}"
    break

  case "sles-12":
  case "sles-15":
    galeraUrl = "http://repo/jenkins/DEVBUILDS/galera-${galeraVersion}/latest/rpm/${label}"
    sh "sudo zypper ar -f -C ${galeraUrl} ${repoName}"
    sh "sudo zypper --no-gpg-checks refresh ||:"
    sh "sudo zypper --no-gpg-checks -n install --repo ${repoName} ${rpmPkgNames}"
    break

  default:
    error "Galera installation is not supported on ${label}!"
    break
}



}
