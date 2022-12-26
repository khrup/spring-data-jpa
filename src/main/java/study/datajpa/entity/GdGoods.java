package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//Entity를 만들때 디폴트생성자가 필요함. protected 까지 열어놔야함,
@ToString
@AllArgsConstructor
@Builder
public class GdGoods extends BaseEntity {

    @Id
    private String goodsNo;
    private String goodsNm;

    public GdGoods(String goodsNo, String goodsNm) {
        this.goodsNo = goodsNo;
        this.goodsNm = goodsNm;
    }

    public GdGoods(String goodsNm) {
        this.goodsNm = goodsNm;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "gdGoods")
    private List<GdGoodsImg> gdGoodsImgList = new ArrayList<>();
}
