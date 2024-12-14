package com.banco.comercio.proveedores.bean.request;

import lombok.Data;

@Data
public class AccionistaRequest {
    private String accionistaId;
    private String nombre;
    private String apellido;
    private String capital;
    private String nacionalidad;
}
