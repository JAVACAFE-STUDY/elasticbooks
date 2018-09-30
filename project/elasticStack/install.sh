#!/bin/sh

if [ $# -eq 0 ] ; then
  echo "Please input elastic-stack version"
  exit 1
fi

wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-$1.tar.gz
wget https://artifacts.elastic.co/downloads/logstash/logstash-$1.tar.gz
wget https://artifacts.elastic.co/downloads/kibana/kibana-$1-linux-x86_64.tar.gz
wget https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-$1-linux-x86_64.tar.gz
wget https://artifacts.elastic.co/downloads/beats/metricbeat/metricbeat-$1-linux-x86_64.tar.gz
wget https://artifacts.elastic.co/downloads/beats/heartbeat/heartbeat-$1-linux-x86_64.tar.gz
wget https://artifacts.elastic.co/downloads/beats/packetbeat/packetbeat-$1-linux-x86_64.tar.gz 
wget https://artifacts.elastic.co/downloads/beats/auditbeat/auditbeat-$1-linux-x86_64.tar.gz

DIR_NAME="programs"

if [ ! -d "$DIR_NAME" ]; then
  mkdir $DIR_NAME
fi

tar xzvf elasticsearch-$1.tar.gz && mv elasticsearch-$1 $DIR_NAME/elasticsearch 
tar xzvf logstash-$1.tar.gz && mv logstash-$1 $DIR_NAME/logstash 
tar xzvf kibana-$1-linux-x86_64.tar.gz && mv kibana-$1-linux-x86_64 $DIR_NAME/kibana 
tar xzvf filebeat-$1-linux-x86_64.tar.gz && mv filebeat-$1-linux-x86_64 $DIR_NAME/filebeat 
tar xzvf metricbeat-$1-linux-x86.tar.gz && mv metricbeat-$1-linux-x86 $DIR_NAME/metricbeat 
tar xzvf heartbeat-$1-linux-x86_64.tar.gz && mv heartbeat-$1-linux-x86_64 $DIR_NAME/heartbeat 
tar xzvf packetbeat-$1-linux-x86_64.tar.gz && mv packetbeat-$1-linux-x86_64 $DIR_NAME/packetbeat 
tar xzvf auditbeat-$1-linux-x86_64.tar.gz && mv auditbeat-$1-linux-x86_64 $DIR_NAME/auditbeat

rm -f *.tar.gz

echo "v$1" > version.txt

ENV_PATH="$(pwd)/env.sh"

if cat ~/.bashrc | grep $ENV_PATH ; then
  sed '/elasticStack\/env.sh/d' ~/.bashrc > temp
  mv temp ~/.bashrc
fi

if [ -f "$ENV_PATH" ]; then
  rm $ENV_PATH
fi

echo "#!/bin/bash" >> $ENV_PATH
echo "export ELASTICSEARCH_HOME=$(pwd)/programs/elasticsearch" >> $ENV_PATH
echo "export LOGSTASH_HOME=$(pwd)/programs/logstash" >> $ENV_PATH
echo "export KIBANA_HOME=$(pwd)/programs/kibana" >> $ENV_PATH
echo "export FILEBEAT_HOME=$(pwd)/programs/filebeat" >> $ENV_PATH
echo "export METRICBEAT_HOME=$(pwd)/programs/metricbeat" >> $ENV_PATH
echo "export HEARTBEAT_HOME=$(pwd)/programs/heartbeat" >> $ENV_PATH
echo "export AUDITBEAT_HOME=$(pwd)/programs/auditbeat" >> $ENV_PATH
echo "export PACKETBEAT_HOME=$(pwd)/programs/packetbeat" >> $ENV_PATH
echo "export ELASTICSTACK_HOME=$(pwd)" >> $ENV_PATH
echo ". $(pwd)/env.sh" >> ~/.bashrc
chmod +x $ENV_PATH

. $(pwd)/env.sh
echo "Elasticstack install complete!"
echo "Home path : $ELASTICSTACK_HOME"
