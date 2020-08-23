#!/usr/bin/env groovy

import com.mariadb.es.Build

def call() {
  def slackMessage = """
    ${env.JOB_NAME} - #${env.BUILD_NUMBER}: ${Build.currentBranch} started
    <${env.BUILD_URL}|Open> the build
  """
  slackSend color: "good", message: slackMessage
}
