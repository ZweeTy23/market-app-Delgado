package com.tecdesoftware.market.persistance.mapper;


import com.tecdesoftware.market.domain.Purchase;
import com.tecdesoftware.market.persistence.entity.Compra;
import com.tecdesoftware.market.persistence.mapper.PurchaseItemMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PurchaseItemMapper.class})
public interface PurchaseMapper {

    @Mappings({
            @Mapping(source = "idCompra",target = "purchaseId"),
            @Mapping(source = "idCliente",target = "clientId"),
            @Mapping(source = "fechaCompra",target = "date"),
            @Mapping(source = "medioPago",target = "paymentMethod"),
            @Mapping(source = "comentario",target = "comment"),
            @Mapping(source = "estado",target = "state"),
            @Mapping(source = "productos",target = "items")
    })
    Purchase toPurchase(Compra compra);
    List<Purchase> toPurchases(List<Compra> compras);

    @InheritInverseConfiguration
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "idCompra", expression = "java(purchase.getPurchaseId() == 0 ? null : purchase.getPurchaseId())")
    Compra toCompra(Purchase purchase);

}
