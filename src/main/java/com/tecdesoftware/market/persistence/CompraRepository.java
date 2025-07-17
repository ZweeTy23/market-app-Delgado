package com.tecdesoftware.market.persistence;

import com.tecdesoftware.market.domain.Purchase;
import com.tecdesoftware.market.domain.repository.PurchaseRepository;
import com.tecdesoftware.market.persistence.crud.CompraCrudRepository;
import com.tecdesoftware.market.persistence.entity.Compra;
import com.tecdesoftware.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    private final CompraCrudRepository compraCrudRepository;
    private final PurchaseMapper mapper;

    @Autowired
    public CompraRepository(CompraCrudRepository compraCrudRepository, PurchaseMapper mapper) {
        this.compraCrudRepository = compraCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Purchase> getAll() {
        List<Compra> compras = (List<Compra>) compraCrudRepository.findAll();
        return mapper.toPurchases(compras);
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(mapper::toPurchases);
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        compra.getCompraProductos().forEach(cp -> cp.setCompra(compra));
        Compra saved = compraCrudRepository.save(compra);
        return mapper.toPurchase(saved);
    }
}