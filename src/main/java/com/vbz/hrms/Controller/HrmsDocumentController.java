package com.vbz.hrms.Controller;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.vbz.hrms.Respositoy.HrmsDocumentRepository;
import com.vbz.hrms.model.HrmsDocument;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/mongo/documents")
public class HrmsDocumentController {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "application/pdf",
            "image/jpeg",
            "image/jpg",
            "image/png"
    );

    private final GridFSBucket gridFSBucket;
    private final HrmsDocumentRepository repository;

    public HrmsDocumentController(GridFSBucket gridFSBucket,
                                  HrmsDocumentRepository repository) {
        this.gridFSBucket = gridFSBucket;
        this.repository = repository;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
            @RequestParam Long userId,
            @RequestParam String documentType,
            @RequestParam MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.badRequest().body("File size must be less than 10 MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            return ResponseEntity.badRequest()
                    .body("Only PDF and image files (JPG, PNG) are allowed");
        }

        ObjectId fileId = gridFSBucket.uploadFromStream(
                file.getOriginalFilename(),
                file.getInputStream(),
                new GridFSUploadOptions()
                        .metadata(new org.bson.Document("contentType", contentType))
        );

        HrmsDocument doc = new HrmsDocument();
        doc.setUserId(userId);
        doc.setDocumentType(documentType);
        doc.setFileId(fileId.toHexString());
        doc.setFileName(file.getOriginalFilename());

        repository.save(doc);

        return ResponseEntity.ok("File uploaded successfully");
    }

 

    @GetMapping("/user/{userId}")
    public List<HrmsDocument> fetchByUser(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }


    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> delete(@PathVariable String fileId) {
        gridFSBucket.delete(new ObjectId(fileId));

        Optional<HrmsDocument> doc = repository.findAll()
                .stream()
                .filter(d -> fileId.equals(d.getFileId()))
                .findFirst();

        doc.ifPresent(repository::delete);

        return ResponseEntity.ok("File deleted successfully");
    }
}
