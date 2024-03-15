package org.example.cafe.dao;

import jakarta.transaction.Transactional;
import org.example.cafe.models.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderItemDAO {

    private final SessionFactory sessionFactory;

    public OrderItemDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional
    public void update(Long order_item_id,OrderItem orderItemUpdate){
        Session session = sessionFactory.getCurrentSession();
        OrderItem orderItem = session.get(OrderItem.class,order_item_id);
        orderItem.setItemId(orderItemUpdate.getItemId());
        orderItem.setQuantity(orderItemUpdate.getQuantity());
    }

    @Transactional
    public OrderItem viewOI(Long order_item_id){
        Session session = sessionFactory.getCurrentSession();
        return  session.get(OrderItem.class,order_item_id);
    }
    @Transactional
    public void delete(Long order_item_id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM OrderItem WHERE order.orderId = :orderId";
        session.createQuery(hql).setParameter("orderId", order_item_id).executeUpdate();
    }
}
