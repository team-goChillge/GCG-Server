package com.example.gochillge.domain.post.service;

import com.example.gochillge.domain.post.dto.req.PostReq;
import com.example.gochillge.domain.post.dto.res.PostRes;
import com.example.gochillge.domain.post.entity.Post;
import com.example.gochillge.domain.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    public List<PostRes> get() {
        return postRepository.findAll().stream()
                .map(PostRes :: fromEntity)
                .collect(Collectors.toList());
    }


    public PostRes getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return PostRes.of(post);
    }


    public void create(PostReq postReq) {
        Post post = postReq.toEntity();
        postRepository.save(post);
    }

    public PostRes update(Long id, PostReq postReq) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.patch(post);
        return PostRes.of(postRepository.save(post));
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
