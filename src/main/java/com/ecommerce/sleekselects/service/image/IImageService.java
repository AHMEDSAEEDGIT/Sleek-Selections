package com.ecommerce.sleekselects.service.image;

import com.ecommerce.sleekselects.dto.ImageDto;
import com.ecommerce.sleekselects.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files , Long productId);
    void updateImage(MultipartFile file , Long imageId);

}
