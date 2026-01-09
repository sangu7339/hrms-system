package com.vbz.hrms.Respositoy;

import com.vbz.hrms.model.HrmsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HrmsDocumentRepository
        extends MongoRepository<HrmsDocument, String> {

    List<HrmsDocument> findByUserId(Long userId);
}
