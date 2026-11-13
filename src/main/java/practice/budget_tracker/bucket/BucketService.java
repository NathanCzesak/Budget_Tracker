package practice.budget_tracker.bucket;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import practice.budget_tracker.exceptions.BadRequestException;

import java.math.BigDecimal;
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

        if(bucket.getBucketName() == null || bucket.getBucketName().isBlank()) {
            throw new BadRequestException("Bucket name is required");
        }

        if(bucket.getCurrentBalance() == null){
            bucket.setCurrentBalance(BigDecimal.ZERO);
        }

        if(bucket.getCurrentBalance().compareTo(BigDecimal.ZERO) < 0){
            throw new BadRequestException("Starting balance is negative");
        }

        bucket.setBucketName(bucket.getBucketName().trim());

        if(bucketRepository.existsByBucketNameIgnoreCase(bucket.getBucketName())) {
            throw new BadRequestException("Bucket with this name already exists");
        }

        return bucketRepository.save(bucket);

    }

    public Bucket updateBucket(Long id, Bucket newBucket) {
        if (!bucketRepository.existsById(id)){
            throw new EntityNotFoundException("Bucket with id " + id + " not found");
        }

        Bucket bucketToUpdate = bucketRepository.findById(id).get();
        String originalName = bucketToUpdate.getBucketName();
        String newName = newBucket.getBucketName();

        if(newName == null || newName.isBlank() ) {}
        else if (!originalName.equalsIgnoreCase(newName.trim())) {
            newName = newName.trim();
            if(bucketRepository.existsByBucketNameIgnoreCase(newName)) {
                throw new BadRequestException("Bucket with this name already exists");
            }
            bucketToUpdate.setBucketName(newName.trim());
        }

//        if(newBucket.getBucketName() == null || newBucket.getBucketName().isBlank()){
//            bucketToUpdate.setBucketName(bucketRepository.findById(id).get().getBucketName());
//        } else{
//            if(!bucketToUpdate.getBucketName().equals(newBucket.getBucketName())){
//                bucketToUpdate.setBucketName(newBucket.getBucketName().trim());
//            }
//            if(bucketRepository.existsByBucketNameIgnoreCase(bucketToUpdate.getBucketName())) {
//                throw new BadRequestException("Bucket with this name already exists");
//            }
//        }



        if(newBucket.getCurrentBalance() == null){
            bucketToUpdate.setCurrentBalance(bucketRepository.findById(id).get().getCurrentBalance());
        } else{
            bucketToUpdate.setCurrentBalance(newBucket.getCurrentBalance());
        }

        return bucketRepository.save(bucketToUpdate);
    }

    public void deleteBucketById(Long id) {
        if(!bucketRepository.existsById(id)) {
            throw new EntityNotFoundException("Bucket with id " + id + " does not exist");
        }
        this.bucketRepository.deleteById(id);
    }



}
