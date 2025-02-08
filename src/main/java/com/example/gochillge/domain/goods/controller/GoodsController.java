package com.example.gochillge.domain.goods.controller;

import com.example.gochillge.domain.goods.dto.res.GoodsRes;
import com.example.gochillge.domain.goods.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
@Tag(name = "goods", description = "물품 관련 기능")
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/log")
    @Operation(summary = "물품 사용 로그 조회", description = "모든 물품 사용 로그들을 조회합니다.")
    public List<GoodsRes> log() { return goodsService.get(); }
}
