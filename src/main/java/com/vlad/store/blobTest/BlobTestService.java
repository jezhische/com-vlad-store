package com.vlad.store.blobTest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlobTestService {

    private BlobTestRepository repository;

    public BlobTestService(BlobTestRepository repository) {
        this.repository = repository;
    }

    public void save(BlobTest test) {

    }
}
