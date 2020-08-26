#!/usr/bin/env groovy

import com.mariadb.es.Build

def call() {
  def slackMessage = """
    ${env.JOB_NAME} - #${env.BUILD_NUMBER}: ${Build.currentBranch} is started
    <${env.BUILD_URL}|Open> the build
  """
  Build.slackResponse = slackSend color: "good", botUser: true, message: slackMessage

  def details = ""

  if (Build.changedFiles == 0) {
    details = "${Build.changedFiles} file(s) changed in this run."
  } else {
    details = "${Build.changedFiles} file(s) changed by ${Build.changedFilesList}"
  }
  slackSend(color: "good", channel: Build.slackResponse.threadId, message: details)
}
