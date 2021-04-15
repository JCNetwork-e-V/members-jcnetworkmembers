package com.jcnetwork.members.model.event;

import com.jcnetwork.members.model.dto.ConsultancyCreationDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnConsultancyCreationEvent extends ApplicationEvent {

    private ConsultancyCreationDto consultancyCreationDto;

    public OnConsultancyCreationEvent(ConsultancyCreationDto consultancyCreationDto) {
        super(consultancyCreationDto);
        this.consultancyCreationDto = consultancyCreationDto;
    }
}
