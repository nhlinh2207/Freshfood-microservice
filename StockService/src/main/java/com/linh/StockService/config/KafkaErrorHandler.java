package com.linh.StockService.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

@Configuration
@Slf4j
public class KafkaErrorHandler implements CommonErrorHandler {

    @Override
    public void handleRecord(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        handle(thrownException, consumer);
    }

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        handle(thrownException, consumer);
    }

    private void handle(Exception exception, Consumer<?, ?> consumer) {
        log.error("Exception thrown", exception);
//       We can use the seek() method from the Consumer interface to manually change the current offset position for a particular partition within a topic. Simply put, we can use it to reprocess or skip messages as needed based on their offsets.
//
//      In our case, if the exception is an instance of RecordDeserializationException, we’ll call the seek() method with the topic partition and the next offset:

        if (exception instanceof RecordDeserializationException) {
            RecordDeserializationException ex = (RecordDeserializationException) exception;
            consumer.seek(ex.topicPartition(), ex.offset() + 1L);
            consumer.commitSync();
        } else {
            log.error("Exception not handled", exception);
        }
//        As we can notice, we need to call the commitSync() from the Consumer interface. This will commit the offset and ensure that the new position is acknowledged and persisted by the Kafka broker.
//        This step is crucial, as it updates the offset committed by the consumer group, indicating that messages up to the adjusted position have been successfully processed.
    }
}
