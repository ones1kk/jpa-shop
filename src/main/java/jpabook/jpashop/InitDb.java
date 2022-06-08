package jpabook.jpashop;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService service;

    @PostConstruct
    public void init() {
        service.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member memberA = new Member();
            memberA.setName("userA");
            memberA.setAddress(new Address("aa", "aa", "aa"));

            Book bookA = new Book();
            bookA.setName("A");
            bookA.setPrice(10000);
            bookA.setStockQuantity(100);

            em.persist(bookA);

            Book bookB = new Book();
            bookB.setName("A");
            bookB.setPrice(20000);
            bookB.setStockQuantity(100);

            em.persist(bookB);

            em.persist(memberA);

            OrderItem orderItem1 = OrderItem.createOrderItem(bookA, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(bookB, 40000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(memberA.getAddress());
            Order.createOrder(memberA, delivery, orderItem1, orderItem2);
        }
    }
}
