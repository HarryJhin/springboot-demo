package com.example.springbootdemo.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest // H2 DB를 자동으로 실행해준다.
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After // 단위 테스트가 끝날 때마다 수행되는 메서드 지정
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        String title = "Test Title";
        String content = "Test Content";

        postsRepository.save(Posts.builder() // posts 테이블에 insert/update 쿼리를 실행, id가 있다면 update, id가 없다면 insert
                .title(title)
                .content(content)
                .author("joojinhyun00@gmail.com")
                .build());

        List<Posts> postsList = postsRepository.findAll(); // posts 테이블에 있는 모든 데이터를 조회하는 메서드

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
