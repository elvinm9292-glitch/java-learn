package java_learning.entity;

import jakarta.persistence.*;
import java_learning.enums.OrderStatus;

import java.time.Instant;

@Entity
@Table(name = "orders", schema = "java_learn")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.CREATED;

    private Instant createdAt = Instant.now();

    protected Order() {
    }

    public Order(OrderStatus status){
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
