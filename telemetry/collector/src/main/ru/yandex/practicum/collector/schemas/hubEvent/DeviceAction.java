package main.ru.yandex.practicum.collector.schemas.hubEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import main.ru.yandex.practicum.collector.enums.ActionType;

@Getter @Setter @ToString
public class DeviceAction {
    private String sensorId;
    private ActionType type;
    private Integer value;
}
