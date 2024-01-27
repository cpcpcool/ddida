package com.runner.ddida.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.runner.ddida.dto.MemberDto;
import com.runner.ddida.model.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberConverter {
	  
	private final ModelMapper modelMapper;
	
	// Entity와 DTO 양방향으로 컨버팅 하는 두 메서드 정의한 것
	public Member convertToEntity(MemberDto memberDto) {
		return modelMapper.map(memberDto,  Member.class);
	}
	
	public MemberDto convertToDto(Member member) {
		return modelMapper.map(member, MemberDto.class);
	}


}
