package com.ecommerce.sleekselects.controller;


import com.ecommerce.sleekselects.exception.AlreadyExistsException;
import com.ecommerce.sleekselects.exception.ResourceNotFoundException;
import com.ecommerce.sleekselects.model.Category;
import com.ecommerce.sleekselects.response.ApiResponse;
import com.ecommerce.sleekselects.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private  final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!" , categories));
        } catch (ResourceNotFoundException e) {
            return   ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:" , INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add/{name}")
    public  ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){

        try {
            Category category =categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success!" , category));

        } catch (AlreadyExistsException e) {
            return  ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage() , null));
        }
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){

        try {
            Category category = categoryService.getCategoryById(categoryId);
            return  ResponseEntity.ok(new ApiResponse("Found" , category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }

    @GetMapping("/by/name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){

        try {
            Category category = categoryService.getCategoryByName(name);
            return  ResponseEntity.ok(new ApiResponse("Found" , category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }


    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){

        try {
            categoryService.deleteCategoryById(categoryId);
            return  ResponseEntity.ok(new ApiResponse("Delete success!" , null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId , @RequestBody Category category){

        try {
            Category updatedCategory = categoryService.updateCategory( category , categoryId);
            return  ResponseEntity.ok(new ApiResponse("Update success!" , updatedCategory ));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }
}
