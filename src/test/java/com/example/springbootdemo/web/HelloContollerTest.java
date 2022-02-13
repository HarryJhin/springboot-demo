package com.example.springbootdemo.web; 

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)                        // 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자(SpringRunner)를 실행
@WebMvcTest(controllers = HelloController.class)    // 스프링 어노테이션 중 web에 집중할 수 있는 어노테이션

public class HelloContollerTest {
    
    @Autowired              // 스프링이 관리하는 Bean을 주입받는다.
    private MockMvc mvc;    // 웹 API 테스트 때 사용, Spring Mvc 테스트 시작점

    @Test
    public void run_hello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))                      // HTTP GET 요청
                .andExpect(status().isOk())             // HTTP Header의 Status 검증 (200 - 정상, 404, 500)
                .andExpect(content().string(hello));    // 응답 본문의 내용 검증
    }

    @Test
    public void run_helloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)        // param: API 테스트 할 때 사용될 요청 파라미터를 설정, 오로지 String 값만 허용
                .param("amount", String.valueOf(amount)))   // 정수는 허용되지 않아 문자열로 변경
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is(name)))    // josnPath: JSON 응답값을 필드 별로 검증할 수 있는 메서드, $를 기준으로 필드명을 명시
                        .andExpect(jsonPath("$.amount", is(amount)));
    }
}
