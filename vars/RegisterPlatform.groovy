#!/usr/bin/env groovy

def call(label) {
  assert label != null

  switch (label) {

    case "sles-12":
    case "sles-15":
      retry(5) {
        echo "Trying to register SLES platform: ${label}"
        sh "sudo rm -fv /etc/SUSEConnect"
        sh "sudo rm -fv /etc/zypp/{repos,services,credentials}.d/*"
        sh "sudo rm -fv /usr/lib/zypp/plugins/services/*"
        sh "sudo sed -i '/^# Added by SMT reg/,+1d' /etc/hosts"
        sh "sudo SUSEConnect --cleanup"
        sh "sudo SUSEConnect --regcode ${SLES_KEY}"
      }
      break

    case "rhel-6":
    case "rhel-7":
    case "rhel-8":
      retry(5) {
        sh "sudo rm -fv /etc/yum.repos.d/rh-cloud.repo"
        sh "sudo subscription-manager register --auto-attach --force --username ${RHEL_CRD_USR} --password ${RHEL_CRD_PSW}"
      }
      break

    default:
      echo "No need to register ${label} platform"
      break
  }

  switch (label) {

    case "rhel-6":
      retry(5) {
        sh "sudo subscription-manager repos --enable=rhel-6-server-optional-rpms"
      }
      break

    case "rhel-7":
      retry(5) {
        "sudo subscription-manager release --set=7.5"
        "sudo subscription-manager repos --enable=rhel-7-server-optional-rpms"
      }
      break

    case "rhel-8":
      retry(5) {
        sh "sudo subscription-manager release --set=8.0"
        sh "sudo subscription-manager repos --enable=codeready-builder-for-rhel-8-x86_64-rpms"
      }
      break

    default:
      break
  }

}
