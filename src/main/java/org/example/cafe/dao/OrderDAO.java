package org.example.cafe.dao;

import jakarta.transaction.Transactional;
import org.example.cafe.models.Order;
import org.example.cafe.models.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDAO {
    private final SessionFactory sessionFactory;

    public OrderDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Order order){
        Session session = sessionFactory.getCurrentSession();
        List<OrderItem> lists = order.getItems();
        for (OrderItem list:lists) {
            session.persist(list);
        }
        session.persist(order);
    }

    @Transactional
    public Order view(Long orderId){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Order.class,orderId);
    }

    @Transactional
    public void delete(Long orderId){
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class,orderId);
        session.remove(order);
    }



}
