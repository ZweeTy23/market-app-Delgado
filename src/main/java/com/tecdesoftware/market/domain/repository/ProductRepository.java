package com.tecdesoftware.market.domain.repository;


import java.util.List;
import java.util.Optional;

import com.tecdesoftware.market.domain.Product;

public interface ProductRepository {
    List<Product> getAll();
    Optional<List<Product>> getByCategory(int CategoryID);
    Optional<List<Product>> getScarseProducts(int quantity);
    Optional<Product> getProduct (int productID);
    Product save(Product product);
    void delete(int productId);



}
