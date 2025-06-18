package com.ahmedabad.csr.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ahmedabad.csr.entities.Documents;

public interface DocumentService {
Documents addDocuments(Documents documents);
 Optional<Documents> getDocumentById(int id);
 void deleteDocument(int id);
 Page<Documents> getAlldocument(Pageable pageable);
 Page<Documents> getDocumentsBytype(String documentType, Pageable pageable);
 Documents updateDocument(int id, Documents documentDetails);
}
