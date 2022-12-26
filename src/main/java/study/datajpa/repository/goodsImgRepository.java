package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.GdGoodsImg;

public interface goodsImgRepository extends JpaRepository<GdGoodsImg, GdGoodsImgId> {
}
