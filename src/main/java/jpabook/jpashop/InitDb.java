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
        service.dbInit2();
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
            em.persist(memberA);

            Book bookA = new Book();
            bookA.setName("A");
            bookA.setPrice(10000);
            bookA.setStockQuantity(100);
            em.persist(bookA);

            Book bookB = new Book();
            bookB.setName("B");
            bookB.setPrice(20000);
            bookB.setStockQuantity(200);
            em.persist(bookB);

            OrderItem orderItem1 = OrderItem.createOrderItem(bookA, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(bookB, 40000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(memberA.getAddress());
            Order order = Order.createOrder(memberA, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member memberB= new Member();
            memberB.setName("userB");
            memberB.setAddress(new Address("bb", "bb", "bb"));
            em.persist(memberB);

            Book bookC = new Book();
            bookC.setName("C");
            bookC.setPrice(10000);
            bookC.setStockQuantity(300);
            em.persist(bookC);

            Book bookD = new Book();
            bookD.setName("D");
            bookD.setPrice(20000);
            bookD.setStockQuantity(400);
            em.persist(bookD);

            OrderItem orderItem1 = OrderItem.createOrderItem(bookC, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(bookD, 40000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(memberB.getAddress());
            Order order = Order.createOrder(memberB, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

    }
}
