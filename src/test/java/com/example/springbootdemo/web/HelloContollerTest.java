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
}
