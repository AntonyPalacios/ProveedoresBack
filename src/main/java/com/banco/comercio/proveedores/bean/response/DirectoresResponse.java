package com.banco.comercio.proveedores.bean.response;

import com.banco.comercio.proveedores.bean.request.Documento;
import lombok.Data;

@Data
public class DirectoresResponse extends Documento {
    private String directorId;
    private String nombre;
    private String apellido;
    private String nacionalidad;

}
