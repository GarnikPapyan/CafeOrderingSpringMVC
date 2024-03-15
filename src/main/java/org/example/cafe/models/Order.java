package org.example.cafe.models;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "waiter_id", nullable = false)
    private Integer waiterId;

    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;

    @Column(name = "order_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime orderTime;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items;

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Order() {
    }

    public Order(Integer waiterId, Integer tableNumber) {
        this.waiterId = waiterId;
        this.tableNumber = tableNumber;
    }

    public Long getOrderId() {
        return orderId;
    }


    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

}
