package practice.budget_tracker.bucket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    boolean existsByBucketNameIgnoreCase(String bucketName);
}
