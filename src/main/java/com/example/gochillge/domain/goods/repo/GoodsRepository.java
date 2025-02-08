package com.example.gochillge.domain.goods.repo;

import com.example.gochillge.domain.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
