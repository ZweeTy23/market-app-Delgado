package com.tecdesoftware.market.persistence;

import com.tecdesoftware.market.domain.repository.PurchaseRepository;
import com.tecdesoftware.market.domain.Purchase;
import com.tecdesoftware.market.persistence.crud.CompraCrudRepository;
import com.tecdesoftware.market.persistence.entity.Compra;
import com.tecdesoftware.market.persistance.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;


    @Override
    public List<Purchase> getAll() { //Iteration convertida en Lista
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByidCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);

        Optional.ofNullable(compra.getProductos())
                .ifPresent(productos -> productos.forEach(producto -> producto.setCompra(compra)));

        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
