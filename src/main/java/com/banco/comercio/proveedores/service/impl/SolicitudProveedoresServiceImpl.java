package com.banco.comercio.proveedores.service.impl;

import com.banco.comercio.proveedores.loan.LoanProcessCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.comercio.proveedores.bean.request.SolicitudProveedoresRequest;
import com.banco.comercio.proveedores.bean.response.CambioEstadoResponse;
import com.banco.comercio.proveedores.bean.response.SolicitudProveedoresResponse;
import com.banco.comercio.proveedores.fitbank.beans.response.ProveedoresLoan;
import com.banco.comercio.proveedores.service.SolicitudProveedoresService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SolicitudProveedoresServiceImpl implements SolicitudProveedoresService {

    @Autowired
    private LoanProcessCore loanCore;

    @Override
    public List<ProveedoresLoan> getAll() throws Exception {
        return loanCore.getProveedoresAll();
    }

    @Override
    public List<ProveedoresLoan> getProveedoresByRazonOrDoc(Optional<String> razon, Optional<String> numDoc) throws Exception {
    	return loanCore.getProveedoresByDocOrNom(razon, numDoc);
    }

    @Override
    public SolicitudProveedoresResponse getById(String id) {
        return null;
    }

    @Override
    public CambioEstadoResponse saveSolicitudProveedor(SolicitudProveedoresRequest request) throws Exception {
//        CambioEstadoResponse response = new CambioEstadoResponse();
//        DirectoresResponse directoresResponse = loanCore.saveSolicitudDirectores(request);
//        GerenteResponse gerenteResponse = loanCore.saveSolicitudGerente(request);
//        AccionistaResponse accionistaResponse = loanCore.saveSolicitudAccionista(request);
//        ProductosResponse productosResponse = loanCore.saveSolicitudProductos(request);
//        if (Objects.nonNull(directoresResponse) && Objects.nonNull(gerenteResponse) &&
//                Objects.nonNull(accionistaResponse) && Objects.nonNull(productosResponse)){
//            request.getAccionistaRequest().setAccionistaId(accionistaResponse.getAccionistaId());
//            request.getDirectoresRequest().setDirectorId(directoresResponse.getDirectorId());
//            request.getGerenteRequest().setGerenteId(gerenteResponse.getGerenteId());
//            request.getProductosRequest().setProductoId(productosResponse.getProductoId());
//            response = loanCore.saveSolicitud(request);
//        }
//
//        return response;
    	return null;
    }

    @Override
    public CambioEstadoResponse saveProveedor(ProveedoresLoan request) throws Exception {
    	return loanCore.saveProveedores(request);
    }

    @Override
    public CambioEstadoResponse updateProveedor(ProveedoresLoan request) {
        return loanCore.updateProveedor(request);
    }
}
