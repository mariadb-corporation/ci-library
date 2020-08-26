#!/usr/bin/env groovy

import com.mariadb.es.Build

def call(status) {
  assert status != null

  def messageColor = getMessageColor(status)
  def messageToCommiter
  def slackReaction

  switch (status) {
    case "SUCCESS":
      messageToCommiter = "Now you can integrate your changes into main branch! :beer:"
      break;
    case "ABORTED":
    case "FAILURE":
      messageToCommiter = "Please check and fix your build! :skull:"
      break;
    case "UNSTABLE":
      messageToCommiter = "Something is not OK, please check your build manually!"
      break;
    default:
      messageToCommiter = "Something went wrong, please check your build manually!"
      break;
  }

  def slackMessage = """
    Build status is ${status}, GitHub status is set
    ${messageToCommiter}
    """
  slackSend(color: messageColor, channel: Build.slackResponse?.threadId, message: slackMessage)

}
