package study.datajpa.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.GdGoods;
import study.datajpa.entity.GdGoodsImg;

import java.util.List;

@SpringBootTest
@Transactional
class goodsRepositoryTest {

    @Autowired
    goodsRepository goodsRepository;

    @Autowired
    goodsImgRepository goodsImgRepository;

    @BeforeEach
    public void sampleCreateGoods() {

        String goodsNo = "G1";

        GdGoods 상품A = new GdGoods(goodsNo, "상품A");

        goodsRepository.save(상품A);

        GdGoodsImg 상품이미지A = new GdGoodsImg(goodsNo, "1", "A");
        상품이미지A.setGdGoods(상품A);
        GdGoodsImg 상품이미지A_1 = new GdGoodsImg(goodsNo, "2", "A");
        상품이미지A_1.setGdGoods(상품A);

        goodsImgRepository.save(상품이미지A);
        goodsImgRepository.save(상품이미지A_1);
    }

    @Test
    @DisplayName("상품 양방향 테스트")
    @Rollback(false)
    public void gdGoodsOneToManyTest() {
        List<GdGoods> allFindGoodsList = goodsRepository.findAll();
        for (GdGoods allFindGoods : allFindGoodsList) {
            System.out.println("allFindGoods = " + allFindGoods);
            System.out.println("getGdGoodsImgList = " + allFindGoods.getGdGoodsImgList());
        }

        List<GdGoodsImg> allFindGoodsImg = goodsImgRepository.findAll();
        for (GdGoodsImg gdGoodsImg : allFindGoodsImg) {
            System.out.println("gdGoodsImg = " + gdGoodsImg);
        }

        goodsRepository.deleteGdGoodsByGoodsNo(1L);
//        GdGoodsImg findGdGoodsImg = goodsImgRepository.findById(GdGoodsImgId.builder().goodsNo(1L).imgSeq(1L).build()).get();
//
//        goodsImgRepository.delete(findGdGoodsImg);
    }
}