package com.banco.comercio.proveedores.bean.request;

import lombok.Data;

@Data
public class GerenteRequest extends Documento{
    private String gerenteId;
    private String nombres;
    private String apellidos;
    private String nacionalidad;
}
