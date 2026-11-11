package practice.budget_tracker;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;

    public BucketService(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    public List<Bucket> listAllBuckets() {
        List<Bucket> buckets = bucketRepository.findAll();
        if(buckets.isEmpty()) {
            throw new EntityNotFoundException("No buckets found");
        }
        return bucketRepository.findAll();
    }

    public Bucket addBucket(Bucket bucket) {
        return bucketRepository.save(bucket);
    }

    public Bucket updateBucket(Long id, Bucket bucket) {
        if (!bucketRepository.existsById(id)){
            throw new EntityNotFoundException("Bucket with id " + id + " not found");
        }
        Bucket bucketToUpdate = bucketRepository.findById(id).get();
        bucketToUpdate.setBucketName(bucket.getBucketName());
        bucketToUpdate.setCurrentBalance(bucket.getCurrentBalance());
        return bucketRepository.save(bucketToUpdate);
    }

    public void deleteBucketById(Long id) {
        if(!bucketRepository.existsById(id)) {
            throw new EntityNotFoundException("Bucket with id " + id + " does not exist");
        }
        this.bucketRepository.deleteById(id);
    }



}
