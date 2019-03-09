package com.vlad.store.service;

import com.vlad.store.model.ProductImage;
import com.vlad.store.repository.FileUploadModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileUploadModelServiceImpl implements FileUploadModelService {

    private final FileUploadModelRepository repository;

    @Autowired
    public FileUploadModelServiceImpl(FileUploadModelRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductImage saveFile(ProductImage productImage) {
        return repository.saveAndFlush(productImage);
    }

    @Override
    public ProductImage updateFile(ProductImage productImage) {
        return repository.saveAndFlush(productImage);
    }

    @Override
    public Optional<ProductImage> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ProductImage> findAllByProductId(Long productId) {
        return repository.findAllByProductId(productId);
    }

    @Override
    public void delete(ProductImage fileModel) {
        repository.delete(fileModel);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
