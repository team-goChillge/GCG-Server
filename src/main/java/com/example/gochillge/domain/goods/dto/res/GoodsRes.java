package com.example.gochillge.domain.goods.dto.res;

import com.example.gochillge.domain.goods.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class GoodsRes {
    private Long id;
    private String thing;
    private LocalDateTime createdAt;

    public static GoodsRes fromEntity(Goods goods) {
        return new GoodsRes(
                goods.getId(),
                goods.getThing(),
                goods.getCreatedAt()
        );
    }

    public static List<GoodsRes> of(List<Goods> req) {
        return req.parallelStream()
                .map(GoodsRes::of)
                .toList();
    }

    public static GoodsRes of(Goods goods) {
        return new GoodsRes(
                goods.getId(),
                goods.getThing(),
                goods.getCreatedAt()
        );
    }
}
