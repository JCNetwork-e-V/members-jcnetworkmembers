package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.Member;
import com.jcnetwork.members.model.data.UserDetails;
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

        UserDetails userDetails = new UserDetails();
        if (member.getUserDetails() != null){
            userDetails = member.getUserDetails();
        }

        return new MemberDto(
                member.getEmail(),
                userDetailsMapper.toDto(userDetails),
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
