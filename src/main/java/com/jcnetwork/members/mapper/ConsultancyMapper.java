package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.dto.ConsultancyDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsultancyMapper {

    public ConsultancyDto toDto(Consultancy consultancy) {
        return new ConsultancyDto(
                consultancy.getId(),
                consultancy.getConsultancyDetails().getName(),
                consultancy.getConsultancyDetails().getCity(),
                consultancy.getConsultancyDetails().getDomain(),
                consultancy.getMembers().size()
        );
    }

    public List<ConsultancyDto> toDto(List<Consultancy> consultancies) {
        List<ConsultancyDto> consultancyDtos = new ArrayList<>();
        for(Consultancy consultancy : consultancies){
            consultancyDtos.add(toDto(consultancy));
        }
        return consultancyDtos;
    }
}
