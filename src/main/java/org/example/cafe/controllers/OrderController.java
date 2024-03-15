package org.example.cafe.controllers;

import org.example.cafe.dao.BillingDAO;
import org.example.cafe.dao.MenuItemDAO;
import org.example.cafe.dao.OrderDAO;

import org.example.cafe.dao.OrderItemDAO;
import org.example.cafe.models.MenuItem;
import org.example.cafe.models.Order;


import org.example.cafe.models.OrderItem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final MenuItemDAO menuItemDAO;
    private final BillingDAO billingDAO;
    public OrderController(OrderDAO orderDAO, OrderItemDAO orderItemDAO, MenuItemDAO menuItemDAO,BillingDAO billingDAO) {
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.menuItemDAO = menuItemDAO;
        this.billingDAO = billingDAO;
    }


    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody Order order){
        orderDAO.save(order);
        billingDAO.save(order);
        return ResponseEntity.ok("Order items created successfully");
    }

    @GetMapping("/view/{orderId}")
    public String viewOrders(ModelMap modelMap,@PathVariable("orderId") Long orderId){
        Order order = orderDAO.view(orderId);
        OrderItem orderItem = orderItemDAO.viewOI(orderId);
        MenuItem menuItem = menuItemDAO.getById(orderItem.getItemId());
        modelMap.put("order",order);
        modelMap.put("orderItem",orderItem);
        modelMap.put("menuItem",menuItem);
        return "order/orders";
    }

    @PutMapping("/modify/{orderId}")
    public ResponseEntity<String> updateOrderDetails(@PathVariable("orderId") Long orderId, @RequestBody OrderItem orderItem){
        orderItemDAO.update(orderId,orderItem);
        return ResponseEntity.ok("Order items updated successfully");
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId){
        billingDAO.delete(orderId);
        orderDAO.delete(orderId);
        orderItemDAO.delete(orderId);
        return ResponseEntity.ok("Order items deleted successfully");
    }

}
