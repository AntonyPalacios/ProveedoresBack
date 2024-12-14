package com.banco.comercio.proveedores.bean.response;

import lombok.Data;

@Data
public class ProductosResponse {
    private String productoId;
    private String nombreProducto;
    private String rubro;
    private String sector;
}
