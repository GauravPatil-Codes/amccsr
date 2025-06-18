package com.ahmedabad.csr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.Documents;
import com.ahmedabad.csr.repository.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    
    @Override
    public Documents addDocuments(Documents documents) {
        // Add validation if needed
               return documentRepository.save(documents);
    }

    @Override
    public Optional<Documents> getDocumentById(int id) {
        return documentRepository.findById(id);
    }

  @Override
    public void deleteDocument(int id) {
        if (!documentRepository.existsById(id)) {
            throw new RuntimeException("Company not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }

    @Override
    public Page<Documents> getAlldocument(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

     @Override
    public Page<Documents> getDocumentsBytype(String documentType, Pageable pageable) {
        return documentRepository.findBydocumentType(documentType, pageable);
    }

       @Override
    public Documents updateDocument(int id, Documents documentDetails) {
        Documents document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        
        // Update fields
        if (documentDetails.getDocumenttitle() != null) {
            document.setDocumenttitle(documentDetails.getDocumenttitle());
        }
        if (documentDetails.getDocumentshortdesc() != null) {
            document.setDocumentshortdesc(documentDetails.getDocumentshortdesc());
        }
        if (documentDetails.getDocumentType()!=null) {
            document.setDocumentType(documentDetails.getDocumentType());
        }
        if (documentDetails.getDocumenturl() != null) {
            document.setDocumenturl(documentDetails.getDocumenturl());
        }
        
        return documentRepository.save(document);
    }

}
