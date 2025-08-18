package com.ecommerce.sleekselects.service.product;

import com.ecommerce.sleekselects.dto.ProductDto;
import com.ecommerce.sleekselects.model.Product;
import com.ecommerce.sleekselects.request.AddProductRequest;
import com.ecommerce.sleekselects.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProducts(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest request , Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand , String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
//    public List<ProductDto> getConvertedProducts(List<Product> products) ;


    }
