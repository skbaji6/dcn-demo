package com.samay.tech.dcn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.samay.tech.amqp.config.KafkaClient;
import com.samay.tech.events.NotificationEvent;
import com.samay.tech.model.Notification;

import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.dcn.QueryChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;

@Slf4j
public class DCNListener implements DatabaseChangeListener {

    @Setter
    @NonNull
    private DatabaseChangeRegistration databaseChangeRegistration;

    @Autowired
    private KafkaClient KafkaClient;
    
    @Value(value = "${message.topic.name}")
    private String topicName;

    @Override
    public void onDatabaseChangeNotification(DatabaseChangeEvent databaseChangeEvent) {
        if (databaseChangeEvent.getRegId() == databaseChangeRegistration.getRegId()) {
            log.info("Database change event received for table {}", databaseChangeEvent.getDatabaseName());
            QueryChangeDescription[] queryChanges = databaseChangeEvent.getQueryChangeDescription();
            if (queryChanges != null) {

                for (QueryChangeDescription queryChange : queryChanges) {

                    TableChangeDescription[] tcds = queryChange.getTableChangeDescription();
                    for (TableChangeDescription tableChange: tcds) {
                        RowChangeDescription[] rcds = tableChange.getRowChangeDescription();

                        for (RowChangeDescription rcd : rcds) {
                            log.info("Registration information changed with rowid {} and type operation {}", rcd.getRowid(), rcd.getRowOperation().name());
                            Notification notification = Notification.builder().table(tableChange.getTableName())
                                    .operation(rcd.getRowOperation().name())
                                    .rowId(rcd.getRowid().stringValue()).build();
                            //applicationEventPublisher.publishEvent(new NotificationEvent(this, notification));
                            KafkaClient.sendMessageWithCallBack(topicName, new NotificationEvent(notification));
                        }

                    }
                }
            }
        }
    }
}