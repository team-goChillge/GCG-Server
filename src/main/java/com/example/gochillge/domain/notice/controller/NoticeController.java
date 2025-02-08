package com.example.gochillge.domain.notice.controller;

import com.example.gochillge.domain.notice.dto.req.NoticeReq;
import com.example.gochillge.domain.notice.dto.res.NoticeRes;
import com.example.gochillge.domain.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
@Tag(name = "notice", description = "공지글 기능")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/get")
    @Operation(summary = "공지글 조회", description = "모든 공지글을 조회합니다.")
    public List<NoticeRes> getPost() {
        return noticeService.get();
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "공지글 상세 조회", description = "특정 공지글를 조회합니다.")
    public ResponseEntity<NoticeRes> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getPost(id));
    }

    @PostMapping("/post")
    @Operation(summary = "공지글 등록", description = "공지글을 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody NoticeReq noticeReq) {
        noticeService.post(noticeReq);
    }

    @PatchMapping("/patch/{id}")
    @Operation(summary = "공지글 수정", description = "특정 공지글을 수정합니다.")
    public void patchPost(@PathVariable Long id,
                                               @RequestBody NoticeReq noticeReq) {
        noticeService.update(id, noticeReq);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "공지글 삭제", description = "공지글을 삭제합니다.")
    public ResponseEntity<NoticeRes> deletePost(@PathVariable Long id) {
        noticeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
