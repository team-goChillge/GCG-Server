package com.example.gochillge.domain.post.dto.req;

import com.example.gochillge.domain.post.entity.Post;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostReq {

    private String title;
    private String content;

    public static List<PostReq> of(List<Post> req) {
        return req.parallelStream()
                .map(PostReq::of)
                .toList();
    }

    public static PostReq of(Post post) {
        return new PostReq(
                post.getTitle(),
                post.getContent()
        );
    }

    public Post toEntity() {
        return Post.builder()
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
