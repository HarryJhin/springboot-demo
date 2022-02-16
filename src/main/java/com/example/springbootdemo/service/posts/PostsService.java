package com.example.springbootdemo.service.posts;

import com.example.springbootdemo.domain.posts.Posts;
import com.example.springbootdemo.domain.posts.PostsRepository;
import com.example.springbootdemo.web.dto.PostsRespoenseDto;
import com.example.springbootdemo.web.dto.PostsSaveRequestDto;
import com.example.springbootdemo.web.dto.PostsUpdateRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
    
    public PostsRespoenseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsRespoenseDto(entity);
    }
}
