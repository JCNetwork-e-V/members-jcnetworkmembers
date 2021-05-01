package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.Member;
import com.jcnetwork.members.model.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberMapper {

    @Autowired
    private UserDetailsMapper userDetailsMapper;

    public MemberDto toDto(Member member) {

        String firstName = null;
        String lastname = null;
        String departmentName = null;
        if (member.getUserDetails() != null){
            firstName = member.getUserDetails().getFirstName();
            lastname = member.getUserDetails().getLastName();
        }

        return new MemberDto(
                member.getUuid().toString(),
                userDetailsMapper.toDto(member.getUserDetails()),
                member.getRoles(),
                member.getCustomFields()
        );
    }

    public List<MemberDto> toDto(List<Member> members) {

        List<MemberDto> membersDtos = new ArrayList<>();
        for(Member member : members){
            membersDtos.add(toDto(member));
        }
        return membersDtos;
    }
}
