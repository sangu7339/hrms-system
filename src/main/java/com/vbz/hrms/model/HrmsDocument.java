package com.vbz.hrms.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "hrms_documents")
public class HrmsDocument {

    @Id
    private String id;

    private Long userId;        
    private String documentType; 
    private String fileId;     
    private String fileName;
}
