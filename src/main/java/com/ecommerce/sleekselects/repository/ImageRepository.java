package com.ecommerce.sleekselects.repository;

import com.ecommerce.sleekselects.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);

}
