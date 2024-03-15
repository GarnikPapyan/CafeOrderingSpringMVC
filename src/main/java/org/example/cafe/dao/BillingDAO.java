package org.example.cafe.dao;

import jakarta.transaction.Transactional;
import org.example.cafe.models.Billing;
import org.example.cafe.models.MenuItem;
import org.example.cafe.models.Order;
import org.example.cafe.models.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;


@Service
public class BillingDAO {
    private final SessionFactory sessionFactory;
    private static final Double TAX = 0.2;
    private static final Double FEE = 0.1;
    private static final Double TIP = 0.1;

    public BillingDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Order order){
        Session session = sessionFactory.getCurrentSession();
        Billing billing = new Billing();
        OrderItem orderItem = session.get(OrderItem.class,order.getOrderId());
        MenuItem menuItem = session.get(MenuItem.class,orderItem.getItemId());
        billing.setOrder(order);
        billing.setTax(menuItem.getPrice()*TAX*orderItem.getQuantity());
        billing.setTip(menuItem.getPrice()*TIP*orderItem.getQuantity());
        billing.setServiceFee(menuItem.getPrice()*FEE*orderItem.getQuantity());
        billing.setTotal(menuItem.getPrice()*(TAX+FEE+TIP)+ menuItem.getPrice()*orderItem.getQuantity());
        session.persist(billing);
    }


    @Transactional
    public Billing view(Long orderId){
        Session session = sessionFactory.getCurrentSession();
        String sql = "FROM Billing WHERE order.orderId = :orderId";
        Query query = session.createQuery(sql);
        query.setParameter("orderId",orderId);
        return (Billing) query.uniqueResult();
    }

    @Transactional
    public void delete(Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "DELETE FROM Billing WHERE order.orderId = :orderId";
        session.createQuery(sql).setParameter("orderId",orderId).executeUpdate();

    }


}
