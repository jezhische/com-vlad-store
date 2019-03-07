package com.vlad.store.service;

import com.vlad.store.model.FileUploadModel;
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
    public FileUploadModel saveFile(FileUploadModel fileUploadModel) {
        return repository.saveAndFlush(fileUploadModel);
    }

    @Override
    public FileUploadModel updateFile(FileUploadModel fileUploadModel) {
        return repository.saveAndFlush(fileUploadModel);
    }

    @Override
    public Optional<FileUploadModel> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<FileUploadModel> findAllByProductId(Long productId) {
        return repository.findAllByProductId(productId);
    }

    @Override
    public void delete(FileUploadModel fileModel) {
        repository.delete(fileModel);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
