package com.banco.comercio.proveedores.service;

import com.banco.comercio.proveedores.fitbank.beans.response.ProveedoresLoan;
import com.banco.comercio.proveedores.bean.request.SolicitudProveedoresRequest;
import com.banco.comercio.proveedores.bean.response.CambioEstadoResponse;
import com.banco.comercio.proveedores.bean.response.SolicitudProveedoresResponse;

import java.util.List;
import java.util.Optional;

public interface SolicitudProveedoresService {
    List<ProveedoresLoan> getAll() throws Exception;
    List<ProveedoresLoan> getProveedoresByRazonOrDoc(Optional<String> razon, Optional<String> numDoc) throws Exception;
    SolicitudProveedoresResponse getById(String id);
    CambioEstadoResponse saveSolicitudProveedor(SolicitudProveedoresRequest request) throws Exception;
    CambioEstadoResponse saveProveedor(ProveedoresLoan request) throws Exception;

    CambioEstadoResponse updateProveedor(ProveedoresLoan request);

}
