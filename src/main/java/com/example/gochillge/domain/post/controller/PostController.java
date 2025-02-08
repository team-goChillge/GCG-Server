package com.example.gochillge.domain.post.controller;

import com.example.gochillge.domain.post.dto.req.PostReq;
import com.example.gochillge.domain.post.dto.res.PostRes;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import com.example.gochillge.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "post", description = "게시글 기능")
public class PostController {
    private final PostService postService;

    @GetMapping("/get")
    @Operation(summary = "게시글 조회", description = "모든 게시물을 조회합니다.")
    public List<PostRes> getPosts() { return postService.get(); }

    @GetMapping("/get/{id}")
    @Operation(summary = "게시글 상세 조회", description = "특정 게시물을 조회합니다.")
    public ResponseEntity<PostRes> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping("/post")
    @Operation(summary = "게시글 등록", description = "게시물을 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody PostReq postReq) {
        postService.create(postReq);
    }

    @PatchMapping("/patch/{id}")
    @Operation(summary = "게시글 수정", description = "특정 게시물을 수정합니다.")
    public ResponseEntity<PostRes> patchPost(@PathVariable Long id, @RequestBody PostReq postReq) {
        PostRes postRes = postService.update(id, postReq);
        return new ResponseEntity<>(postRes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
