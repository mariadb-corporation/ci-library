#!/usr/bin/env groovy

import com.mariadb.es.Build

def call(Status) {
  assert Status != null

  // allow pushes if MTR tests are not OK
  def githubStatus = Status
  if (Status == 'UNSTABLE'){
    githubStatus = 'SUCCESS'
  }

  echo "Setting GitHub status to ${Status}..."
  githubNotify account: 'mariadb-corporation',
               context: "${env.JOB_NAME} (Jenkins)",
               credentialsId: '43ec69b6-4be1-48d0-aa81-29547d360c97',
               repo: 'MariaDBEnterprise',
               sha: Build.currentCommit,
               status: githubStatus,
               targetUrl: "${env.BUILD_URL}"

}
