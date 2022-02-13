package com.example.springbootdemo.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbootdemo.web.dto.HelloResponseDto;

public class HelloResponseDtoTest {
    
    @Test
    public void getter_test() {
        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);      // assertThat: assertj라는 테스트 검증 라이브러리의 검증 메서드이다.
        assertThat(dto.getAmount()).isEqualTo(amount);  // isEqualTo: assertj의 동등 비교 메서드
    }
}
