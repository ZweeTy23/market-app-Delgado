package com.tecdesoftware.market.web.controller;

import com.tecdesoftware.market.domain.Product;
import com.tecdesoftware.market.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Manage Products in the Store")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    @Operation(
            summary = "Get All Products",
            description = "Return a List of All Available Products"
    )
    @ApiResponse(responseCode = "200", description = "Successful Retrieval of Products")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @Operation(
            summary = "Get Product by ID",
            description = "Return a Product by Its ID if It Exists"
    )
    @ApiResponse(responseCode = "200", description = "Product Found")
    @ApiResponse(responseCode = "404", description = "Product Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "ID of the Product to be Retrieved", example = "7", required = true)
            @PathVariable("productId") int productId) {
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(
            summary = "Get Products by Category",
            description = "Return All Products in a Specific Category"
    )
    @ApiResponse(responseCode = "200", description = "Products Found in the Category")
    @ApiResponse(responseCode = "404", description = "No Products Found in the Category")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<List<Product>> getByCategory(
            @Parameter(description = "Category ID", example = "2", required = true)
            @PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(
            summary = "Save a New Product",
            description = "Registers a New Product and Returns the Created Product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example Product",
                                    value = """
                                            {
                                              "name": "Butter Beer",
                                              "categoryId": 2,
                                              "price": 19.50,
                                              "stock": 230,
                                              "active": true
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Product Created Successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Product Data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "409", description = "Product Conflict (Duplicate ID)")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/{productId}")
    @Operation(
            summary = "Delete a Product",
            description = "Deletes a Product by Its ID"
    )
    @ApiResponse(responseCode = "200", description = "Product Deleted")
    @ApiResponse(responseCode = "404", description = "Product Not Found")
    @ApiResponse(responseCode = "400", description = "Invalid Product ID")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the Product to be Deleted", example = "7", required = true)
            @PathVariable("productId") int productId) {
        if (productService.delete(productId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
