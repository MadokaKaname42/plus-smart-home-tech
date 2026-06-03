package ru.yandex.practicum.collector.schemas.hub;

import lombok.ToString;
import ru.yandex.practicum.collector.enums.HubEventType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@ToString(callSuper = true)
@Getter
public class DeviceRemovedEvent extends BaseHubEvent {

    @NotBlank
    private String id;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}
