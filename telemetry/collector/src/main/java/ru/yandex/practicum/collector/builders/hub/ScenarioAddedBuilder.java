package ru.yandex.practicum.collector.builders.hub;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.kafka.KafkaProducer;
import ru.yandex.practicum.collector.schemas.hub.BaseHubEvent;
import ru.yandex.practicum.collector.schemas.hub.ScenarioAddedEvent;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.List;

@Component
public class ScenarioAddedBuilder extends BaseHubBuilder {
    public ScenarioAddedBuilder(KafkaProducer producer) {
        super(producer);
    }

    @Override
    public SpecificRecordBase toAvro(BaseHubEvent hubEvent) {
        ScenarioAddedEvent event = (ScenarioAddedEvent) hubEvent;

        List<ScenarioConditionAvro> conditions = event.getConditions().stream()
                .map(condition -> new ScenarioConditionAvro(
                        condition.getSensorId(),
                        ConditionTypeAvro.valueOf(condition.getType().name()),
                        ConditionOperationAvro.valueOf(condition.getOperation().name()),
                        condition.getValue()))
                .toList();

        List<DeviceActionAvro> actions = event.getActions().stream()
                .map(action -> new DeviceActionAvro(
                        action.getSensorId(),
                        ActionTypeAvro.valueOf(action.getType().name()),
                        action.getValue()))
                .toList();

        return HubEventAvro.newBuilder()
                .setHubId(hubEvent.getHubId())
                .setTimestamp(hubEvent.getTimestamp())
                .setPayload(new ScenarioAddedEventAvro(event.getName(), conditions, actions))
                .build();
    }
}
