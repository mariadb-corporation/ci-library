#!/usr/bin/env groovy

import com.mariadb.es.Build

def call(jobName, jobObj) {
  assert jobName != null
  assert jobObj != null

  def result = jobObj.getResult()
  def messageColor = getMessageColor(result)
  def slackMessage = """
      *${jobName}:* ${result}.
      <${jobObj.absoluteUrl}|Open> the build
      """
  slackSend(color: messageColor, channel: Build.slackResponse?.threadId, message: slackMessage)
}
