package com.banco.comercio.proveedores.bean.request;

import lombok.Data;

@Data
public class DirectoresRequest extends Documento {
    private String directorId;
    private String nombre;
    private String apellido;
    private String nacionalidad;
}
