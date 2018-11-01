#!/bin/bash
if [ $1 = "elasticsearch" ] || [ $1 = "logstash" ] || [ $1 = "kibana" ] || [ $1 = "filebeat" ] || [ $1 = "metricbeat" ] || [ $1 = "heartbeat" ] || [ $1 = "packetbeat" ] || [ $1 = "auditbeat" ] ; then
  $ELASTICSTACK_HOME/stop.sh $1
  $ELASTICSTACK_HOME/start.sh $1 $2 $3
else
  echo "invalid argument : $1"
fi
