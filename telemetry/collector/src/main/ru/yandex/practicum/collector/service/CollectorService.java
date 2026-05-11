package main.ru.yandex.practicum.collector.service;

import main.ru.yandex.practicum.collector.schemas.hubEvent.HubEvent;
import main.ru.yandex.practicum.collector.schemas.sensorEvent.SensorEvent;

public interface CollectorService {

    void collectSensorEvent(SensorEvent event);

    void collectHubEvent(HubEvent event);
}
