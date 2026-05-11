package main.ru.yandex.practicum.collector.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.ru.yandex.practicum.collector.schemas.hubEvent.HubEvent;
import main.ru.yandex.practicum.collector.schemas.sensorEvent.SensorEvent;
import main.ru.yandex.practicum.collector.service.CollectorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class CollectorController {

    private final CollectorService collectorService;

    @PostMapping("/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        log.info("Получено событие датчика: тип={}, id={}, hubId={}",
                event.getType(), event.getId(), event.getHubId());
        collectorService.collectSensorEvent(event);
    }

    @PostMapping("/hubs")
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {
        log.info("Получено событие хаба: тип={}, hubId={}",
                event.getType(), event.getHubId());
        collectorService.collectHubEvent(event);
    }
}

