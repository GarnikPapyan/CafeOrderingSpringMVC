package org.example.cafe.dao;

import jakarta.transaction.Transactional;
import org.example.cafe.models.MenuItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuItemDAO {
    private final SessionFactory sessionFactory;


    public MenuItemDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional
    public List<MenuItem> view(){
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from MenuItem",MenuItem.class).list();
    }
    @Transactional
    public void save(MenuItem menuItem) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(menuItem);
    }

    @Transactional
    public void update(Long itemId,MenuItem menuItemUpdate){
        Session session = sessionFactory.getCurrentSession();
        MenuItem menuItem = session.get(MenuItem.class,itemId);
        menuItem.setCategory(menuItemUpdate.getCategory());
        menuItem.setDescription(menuItemUpdate.getDescription());
        menuItem.setName(menuItemUpdate.getName());
        menuItem.setPrice(menuItemUpdate.getPrice());
        session.persist(menuItem);
    }
    @Transactional
    public void delete(Long itemId) {
        Session session = sessionFactory.getCurrentSession();
        MenuItem menuItem = session.get(MenuItem.class,itemId);
        session.remove(menuItem);
        System.out.println("Deleted was successful");
    }

    @Transactional
    public  MenuItem getById(Long itemId){
        Session session = sessionFactory.getCurrentSession();
        return session.get(MenuItem.class,itemId);
    }
}
