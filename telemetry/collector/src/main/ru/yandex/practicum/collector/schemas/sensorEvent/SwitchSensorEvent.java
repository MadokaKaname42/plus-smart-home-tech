package main.ru.yandex.practicum.collector.schemas.sensorEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import main.ru.yandex.practicum.collector.enums.SensorEventType;

@Getter @Setter @ToString(callSuper = true)
public class SwitchSensorEvent extends SensorEvent {
    private boolean state;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
