package com.example.gochillge.domain.post.dto.res;

import com.example.gochillge.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostRes {

    private Long id;
    private String title;
    private String content;

    public static PostRes fromEntity(Post post) {
        return new PostRes(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
    }

    public static List<PostRes> of(List<Post> req) {
        return req.parallelStream()
                .map(PostRes::of)
                .toList();
    }

    public static PostRes of(Post post) {
        return new PostRes(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
    }
}
