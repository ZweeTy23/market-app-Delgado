package com.tecdesoftware.market.persistence.mapper;

import com.tecdesoftware.market.domain.PurchaseItem;
import com.tecdesoftware.market.persistence.entity.CompraProducto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {

    @Mapping(source = "id.idProducto", target = "productId")
    @Mapping(source = "id.idCompra", target = "purchaseId")
    @Mapping(source = "cantidad", target = "quantity")
    @Mapping(source = "total", target = "total")
    @Mapping(target = "active", expression = "java(\"true\".equals(producto.getEstado()))")
    PurchaseItem toPurchaseItem(CompraProducto producto);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "producto", ignore = true),
            @Mapping(target = "compra", ignore = true)
    })
    CompraProducto toCompraProducto(PurchaseItem item);
}


