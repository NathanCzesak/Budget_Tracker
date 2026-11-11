package practice.budget_tracker;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "buckets")
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bucketName;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @CreationTimestamp
    private Instant createdAt;

    public Bucket() {}
    public Bucket(String bucketName, BigDecimal currentBalance) {
        this.bucketName = bucketName;
        this.currentBalance = currentBalance;
    }

    public long getId() {
        return id;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) { this.bucketName = bucketName; }

    public Instant getCreatedAt() { return createdAt; }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
