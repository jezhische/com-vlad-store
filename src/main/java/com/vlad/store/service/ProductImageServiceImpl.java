package com.vlad.store.service;

import com.vlad.store.model.ProductDetail;
import com.vlad.store.model.ProductImage;
import com.vlad.store.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository repository;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductImage saveFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            System.out.println("********************************************************************* + " +
                    "ProductImage saveFile(MultipartFile file): file.getContentType() = " + file.getContentType());
            ProductImage image = ProductImage.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .build();

            return repository.saveAndFlush(image);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public ProductImage updateFile(ProductImage stored, MultipartFile update) {
        String newFileName = StringUtils.cleanPath(update.getOriginalFilename());

        try {
            if(newFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + newFileName);
            }

            stored.setFileName(newFileName);
            stored.setFileType(update.getContentType());
            stored.setData(update.getBytes());

            return repository.saveAndFlush(stored);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + newFileName + ". Please try again!", ex);
        }
    }

    @Override
    public Optional<ProductImage> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ProductImage> findAllByProductDetails(Long productDetailId) {
        return repository.findAllByProductDetails(productDetailId);
    }

    @Override
    public List<ProductImage> findAllByProductDetailsContainsProductNameOrderByData(ProductDetail detail) {
        return repository.findAllByProductNameOrderByData(detail);
    }

    @Override
    public List<ProductImage> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ProductImage> findByFileName(String fileName) {
        return repository.findByFileName(fileName);
    }

    @Override
    public void delete(ProductImage productImage) {
        repository.delete(productImage);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
