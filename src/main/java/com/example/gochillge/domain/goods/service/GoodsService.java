package com.example.gochillge.domain.goods.service;

import com.example.gochillge.domain.goods.dto.res.GoodsRes;
import com.example.gochillge.domain.goods.repo.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public List<GoodsRes> get() {
        return goodsRepository.findAll().stream()
                .map(GoodsRes:: fromEntity)
                .collect(Collectors.toList());
    }
}
