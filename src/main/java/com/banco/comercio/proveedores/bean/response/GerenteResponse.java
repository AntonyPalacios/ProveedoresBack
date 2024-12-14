package com.banco.comercio.proveedores.bean.response;

import com.banco.comercio.proveedores.bean.request.Documento;
import lombok.Data;

@Data
public class GerenteResponse extends Documento {
    private String gerenteId;
    private String nombres;
    private String apellidos;
    private String nacionalidad;
}
