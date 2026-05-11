package main.ru.yandex.practicum.collector.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

@Slf4j
@Component
public class KafkaEventProducer {
    @Value("${kafka.topics.sensors}")
    private String sensorsTopic;

    @Value("${kafka.topics.hubs}")
    private String hubsTopic;

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    private KafkaProducer<String, byte[]> producer;

    private KafkaProducer<String, byte[]> getProducer() {
        if (producer == null) {
            Properties props = new Properties();
            props.put("bootstrap.servers", bootstrapServers);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
            producer = new KafkaProducer<>(props);
        }
        return producer;
    }

    public void sendSensorEvent(String key, SpecificRecordBase avroRecord) {
        send(sensorsTopic, key, avroRecord);
    }

    public void sendHubEvent(String key, SpecificRecordBase avroRecord) {
        send(hubsTopic, key, avroRecord);
    }

    private void send(String topic, String key, SpecificRecordBase avroRecord) {
        try {
            byte[] bytes = serialize(avroRecord);
            ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, key, bytes);

            getProducer().send(record, (metadata, exception) -> {
                if (exception != null) {
                    log.error("Ошибка отправки в топик {}: {}", topic, exception.getMessage(), exception);
                } else {
                    log.debug("Отправлено в топик={}, partition={}, offset={}",
                            metadata.topic(), metadata.partition(), metadata.offset());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при отправке события в Kafka, топик: " + topic, e);
        }
    }

    private byte[] serialize(SpecificRecordBase record) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DatumWriter<SpecificRecordBase> writer = new SpecificDatumWriter<>(record.getSchema());
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(record, encoder);
        encoder.flush();
        return out.toByteArray();
    }
}
