package main.ru.yandex.practicum.collector.schemas.hubEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import main.ru.yandex.practicum.collector.enums.ConditionOperation;
import main.ru.yandex.practicum.collector.enums.ConditionType;

@Getter @Setter @ToString
public class ScenarioCondition {
    private String sensorId;
    private ConditionType type;
    private ConditionOperation operation;
    private Integer value;
}
