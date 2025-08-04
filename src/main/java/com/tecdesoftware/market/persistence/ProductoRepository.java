package com.tecdesoftware.market.persistence;

import com.tecdesoftware.market.domain.Product;
import com.tecdesoftware.market.domain.repository.ProductRepository;
import com.tecdesoftware.market.persistence.crud.ProductoCrudRepository;
import com.tecdesoftware.market.persistence.entity.Producto;
import com.tecdesoftware.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Le dice a Spring que este repositorio se conecta con la BD
@Repository

public class ProductoRepository implements ProductRepository {


    //Inyectado Automáticamente: Spring crea el objeto por ti
    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper productMapper;

    //Me va a dar todos los productos de mi BD
    public List<Product> getAll(){
        //Convirtiendo un iterable <T> a una lista de prodcutos List<Producto>
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return productMapper.toProducts(productos);
    }
    //Obtiene los productos por categoria ordenados de maner Ascendente
    @Override
    public Optional<List<Product>> getByCategory(int categoryId){
        List<Producto> productos = (List<Producto>) productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }
    //Obtener productos que se vayan a agotar
    @Override
    public Optional<List<Product>> getScarseProducts (int quantity){
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        //No hay un Mapper que convierta una lista de Opcionales, tiene que usar Map
        return productos.map(prods -> productMapper.toProducts(prods));
    }

    //Obtener un producto dado el id
    @Override
    public Optional<Product> getProduct(int productId){
        return productoCrudRepository.findById(productId).map(producto -> productMapper.toProduct(producto));
    }

    //guardar un producto
    @Override
    public Product save(Product product){
        Producto producto = productMapper.toProducto(product);
        return productMapper.toProduct(productoCrudRepository.save(producto));
    }

    //borrar un producto
    @Override
    public void delete(int productId){
        productoCrudRepository.deleteById(productId);
    }
}
