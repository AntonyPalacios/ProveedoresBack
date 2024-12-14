package com.banco.comercio.proveedores.bean.request;

import lombok.Data;

@Data
public class ProductosRequest {
    private String productoId;
    private String nombreProducto;
    private String rubro;
    private String sector;
}
