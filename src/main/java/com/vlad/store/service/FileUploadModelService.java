package com.vlad.store.service;

import com.vlad.store.model.FileUploadModel;

import java.util.List;
import java.util.Optional;

public interface FileUploadModelService {

    FileUploadModel saveFile(FileUploadModel fileUploadModel);

    FileUploadModel updateFile(FileUploadModel fileUploadModel);

    Optional<FileUploadModel> findById(Long id);

    List<FileUploadModel> findAllByProductId(Long productId);

    void delete(FileUploadModel fileModel);

    void deleteById(Long id);
}
