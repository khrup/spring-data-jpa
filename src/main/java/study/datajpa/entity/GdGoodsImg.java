package study.datajpa.entity;

import lombok.*;
import study.datajpa.repository.GdGoodsImgId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//Entity를 만들때 디폴트생성자가 필요함. protected 까지 열어놔야함,
@ToString
@IdClass(GdGoodsImgId.class)
@EqualsAndHashCode
public class GdGoodsImg extends BaseEntity {

    @Id
    @Column(name = "goods_no")
    private String goodsNo;

    @Id
    @Column(name = "img_seq")
    private String imgSeq;

    private String imgPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goodsNo", updatable = false, insertable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private GdGoods gdGoods;

    public GdGoodsImg(String goodsNo, String imgSeq, String imgPath) {
        this.goodsNo = goodsNo;
        this.imgSeq = imgSeq;
        this.imgPath = imgPath;
    }

    public void setGdGoods(GdGoods gdGoods) {
        if (gdGoods != null) {
            gdGoods.getGdGoodsImgList().remove(this);
        }
        this.gdGoods = gdGoods;
        this.gdGoods.getGdGoodsImgList().add(this);
    }
}
