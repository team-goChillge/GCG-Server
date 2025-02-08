package com.example.gochillge.domain.goods.dto.req;

import com.example.gochillge.domain.goods.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsReq {

    private String thing;
    private LocalDateTime createdAt;

    public static List<GoodsReq> of(List<Goods> req) {
        return req.parallelStream()
                .map(GoodsReq::of)
                .toList();
    }

    public static GoodsReq of(Goods req) {
        return new GoodsReq(
                req.getThing(),
                req.getCreatedAt()
        );
    }

    public Goods toEntity() {
        return Goods.builder()
                .thing(getThing())
                .createdAt(getCreatedAt())
                .build();
    }
}
