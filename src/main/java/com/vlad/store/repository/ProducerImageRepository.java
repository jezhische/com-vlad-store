package com.vlad.store.repository;

import com.vlad.store.model.ProducerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerImageRepository extends JpaRepository<ProducerImage, Long> {
}
