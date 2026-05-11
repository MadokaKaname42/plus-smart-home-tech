package main.ru.yandex.practicum.collector.schemas.sensorEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import main.ru.yandex.practicum.collector.enums.SensorEventType;

@Getter @Setter @ToString(callSuper = true)
public class MotionSensorEvent extends SensorEvent {
    private int linkQuality;
    private boolean motion;
    private int voltage;

    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}
