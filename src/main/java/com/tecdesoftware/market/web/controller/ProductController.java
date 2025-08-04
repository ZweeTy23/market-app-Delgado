package com.tecdesoftware.market.web.controller;


import com.tecdesoftware.market.domain.Product;
import com.tecdesoftware.market.domain.service.ProductService;
import com.tecdesoftware.market.persistence.ProductoRepository;
import com.tecdesoftware.market.persistence.entity.Producto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
//Le dice a Spring que va a hacer el controlador de una API REST
@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Manage products in the store")

public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductoRepository productoCrudRepository;


    // ──────────────────── GET /products  ALL ────────────────────
    @GetMapping("/all")
    @Operation(
            summary = "Get all products",
            description = "Retunr a list of all available products"
    )

    @ApiResponse(responseCode = "200", description = "Successful retrieval of products")
    @ApiResponse(responseCode = "500", description = "Internal sever error")
    public List<Product> getAll(){
        return productService.getAll();
    }

    // ───────────── GET /products/{id} (un solo producto) ─────────────

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Return a product by ID if it exists"
    )

    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server erorr")
    public Optional<Product> getProduct(
            @Parameter(description = "ID of the product to be retrieved",
                    example = "7", required = true)
            @PathVariable("id") int productId) {
        return productService.getProduct(productId);
    }

    // ──────── GET /products/category/{id} (por categoría) ────────

    @GetMapping("/category/{id}")
    @Operation(
            summary = "Get product by Category",
            description = "Return all products in a specific category"
    )

    @ApiResponse(responseCode = "200", description = "Product found in the category")
    @ApiResponse(responseCode = "404", description = "Not product found in Category")
    @ApiResponse(responseCode = "500", description = "Internal server erorr")
    public Optional<List<Product>> getByCategory(
            @Parameter(description = "ID of the category to be retrieved",
                    example = "2", required = true)
            @PathVariable("id") int categoryId) {
        return productService.getByCategory(categoryId);
    }

    // ──────────────────── POST /products ────────────────────

    @PostMapping
    @Operation(
            summary = "Save a new product",
            description = "Register a new product and return the created product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example Product",
                                    value = """
                                     {
                                            "name": "Butter beer",
                                            "categoryId": 2,
                                            "price": "19.50",
                                            "stock": 230,
                                            "active": true
                                     }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid product data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Product conflict (duplicate ID)")
    @ApiResponse(responseCode = "500", description = "Internal server erorr")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    // ─────────────── DELETE /products/{id} ───────────────

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a product",
            description = "Delete a product if it exists"
    )

    @ApiResponse(responseCode = "200", description = "Successful delete")
    @ApiResponse(responseCode = "400", description = "Invalid product ID")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not product found for delete")
    @ApiResponse(responseCode = "500", description = "Internal server erorr")
    public boolean delete(
            @Parameter(description = "ID of the product to be deleted",
                    example = "12", required = true)
            @PathVariable("id") int productId) {
        return productService.delete(productId);

    }

}
