package com.vlad.store.repository;

import com.vlad.store.model.FileUploadModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileUploadModelRepository extends JpaRepository<FileUploadModel, Long> {

    FileUploadModel saveAndFlush(FileUploadModel fileModel);

    void delete(FileUploadModel fileModel);

    void deleteById(Long id);

    Page<FileUploadModel> findAll(Pageable pageable);

    Optional<FileUploadModel> findById(Long id);

    List<FileUploadModel> findAllByProductId(Long productId);
}
