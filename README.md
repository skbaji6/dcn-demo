# Database Change Notification - Demo


### Kafka Setup

Download Kafka from https://kafka.apache.org/downloads

create data and logs directories

	+kafkaHome
		|
		--logs
		--data
			|
			--kafka
			--zookeeper

Update zookeeper data directory path in “config/zookeeper.Properties” configuration file. 

	dataDir=C:\kafka_2.13-2.8.0\data\zookeeper

Update Apache Kafka log file path in “config/server.properties” configuration file.

	log.dirs=C:\kafka_2.13-2.8.0\data\kafka

##### Kafka startup Commnds:
	
	cd C:\kafka_2.13-2.8.0\bin\wondows
	> zookeeper-server-start.bat ../../config/zookeeper.properties
	> kafka-server-start.bat ../../config/server.properties


##### SET_ROWID_THRESHOLD Procedure

This procedure configures the maximum number of rows of a table published in a change notification if the rows of the table are modified in a transaction.

Syntax

	DBMS_CQ_NOTIFICATION.SET_ROWID_THRESHOLD (  
	  tbname     IN  VARCHAR2,	
	  threshold  IN  NUMBER);
