package practice.budget_tracker;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buckets")
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping()
    public List<Bucket> findAll() {
        return bucketService.listAllBuckets();
    }

    @PostMapping()
    public Bucket addBucket(@RequestBody Bucket bucket) {
        return bucketService.addBucket(bucket);
    }

    @PutMapping("/{id}")
    public Bucket updateBucket(@PathVariable Long id, @RequestBody Bucket bucket) {
        return bucketService.updateBucket(id, bucket);
    }

    @DeleteMapping("/{id}")
    public void deleteBucket(@PathVariable Long id) {
        bucketService.deleteBucketById(id);
    }

}
