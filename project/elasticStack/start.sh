#!/bin/bash
DIR_NAME=programs
if [ $1 = "elasticsearch" ] ; then
  nohup $ELASTICSTACK_HOME/$DIR_NAME/$1/bin/$1 -Enetwork.host=0.0.0.0 > $ELASTICSTACK_HOME/$DIR_NAME/$1/$1.log 2>&1 &
elif [ $1 = "kibana" ] ; then
  nohup $ELASTICSTACK_HOME/$DIR_NAME/$1/bin/$1 --host=0.0.0.0 > $ELASTICSTACK_HOME/$DIR_NAME/$1/$1.log 2>&1 &
elif [ $1 = "logstash" ] ; then
  nohup $ELASTICSTACK_HOME/$DIR_NAME/$1/bin/$1 > $ELASTICSTACK_HOME/$DIR_NAME/$1/$1.log 2>&1 &
elif echo "$1" | grep 'beat' ; then
  nohup $ELASTICSTACK_HOME/$DIR_NAME/$1/$1 -e -c $ELASTICSTACK_HOME/$DIR_NAME/$1/$1.yml > $ELASTICSTACK_HOME/$DIR_NAME/$1/$1.log 2>&1 &
else
  echo "invalid argument : $1"
  exit 1
fi

echo "$!" > $ELASTICSTACK_HOME/$DIR_NAME/$1/$1.pid
