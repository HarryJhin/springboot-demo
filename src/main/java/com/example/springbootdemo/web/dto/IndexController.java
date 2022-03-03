package com.example.springbootdemo.web.dto;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

import com.example.springbootdemo.config.auth.dto.SessionUser;
import com.example.springbootdemo.service.posts.PostsService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user"); // 로그인 성공시 SessionUser 타입으로 반환되도록 작성됨
        if (user != null) { // 세션에 저장된 값이 있을 때만 model에 userName으로 등록, 없다면 로그인 버튼 보이기
            model.addAttribute("userName", user.getName()); 
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
