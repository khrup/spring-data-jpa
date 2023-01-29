package study.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Product;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class manyToManyTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("ManyToManyTest")
    @Rollback(value = false)
    public void manyToManyTestSave() {
        Product productA = new Product();
        productA.setProductId("A");
        productA.setProductName("상품A");

        em.persist(productA);

        Member member = new Member();
        member.setUsername("회원A");
        member.getProducts().add(productA);

        em.persist(member);
    }
}
