#!/usr/bin/env groovy

import com.mariadb.es.Build

def call(status) {
  assert status != null

  def messageColor

  switch(status) {
    case "SUCCESS":
      messageColor = "good";
      break;
    case "ABORTED":
    case "FAILURE":
      messageColor = "danger";
      break;
    case "UNSTABLE":
      messageColor = "warning";
      break;
    default:
      messageColor = "warning";
      break;
  }
  return messageColor
}
