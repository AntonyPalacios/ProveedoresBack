package com.banco.comercio.proveedores.loan;

import com.banco.comercio.proveedores.bean.request.SolicitudProveedoresRequest;
import com.banco.comercio.proveedores.bean.response.*;
import com.banco.comercio.proveedores.fitbank.beans.response.ProveedoresLoan;
import com.banco.comercio.proveedores.util.DetailHandler;
import com.banco.comercio.proveedores.util.MetodosUtiles;
import com.banco.comercio.proveedores.util.TransactionFitBank;
import com.fitbank.common.Detail;
import com.fitbank.common.Field;
import com.fitbank.common.Record;
import com.fitbank.common.Table;
import com.fitbank.uci.client.UCIClient;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@SuppressWarnings("ALL")
@Slf4j
@Component
public class LoanProcessCore {
	
	@Autowired
	DetailHandler detailHandler;
	
	@Autowired
	private UCIClient uciClient;

	private static Logger log = Logger.getLogger("LoanProcessCore-");
	
	public List<ProveedoresLoan> getProveedoresAll() throws Exception {
		log.info("LoanProcessCore - getProveedoresAll - inicio");
		
		Detail detail = new Detail();
		List<ProveedoresLoan> proveedoresLoanList =  new ArrayList<>();
		
		detail.setType(TransactionFitBank.QUERY.getType());
		detail.setSubsystem(TransactionFitBank.QUERY.getSubsystem());
		detail.setTransaction(TransactionFitBank.QUERY.getTransacction());
		detail.setVersion(TransactionFitBank.QUERY.getVersion());
		
		log.info("detail antes prepareHeader: " + detail.toString());
		detailHandler.prepareHeaderDetail(detail);
		
		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());
		Table table = detail.findTableByAlias("TPOTENCIAL_PROVEEDOR");
		for(Record record: table.getRecords()){
			ProveedoresLoan proveedoresLoan = new ProveedoresLoan();
			proveedoresLoan.setProveedoresId(detail.findFieldByNameCreate("PROVEEDORESID").getStringValue());
			proveedoresLoan.setRazonSocial(detail.findFieldByNameCreate("RAZON_SOCIAL").getStringValue());
			proveedoresLoan.setTipoPersona(detail.findFieldByNameCreate("TIPO_PERSONA").getStringValue());
			proveedoresLoan.setTipoDocumento(detail.findFieldByNameCreate("TIPO_DOCUMENTO").getStringValue());
			proveedoresLoan.setNumeroDocumento(detail.findFieldByNameCreate("NUMERO_DOCUMENTO").getStringValue());
			proveedoresLoan.setCorreo(detail.findFieldByNameCreate("CORREO").getStringValue());
			proveedoresLoan.setTelefono(detail.findFieldByNameCreate("TELEFONO").getStringValue());
			proveedoresLoan.setProyectoId(detail.findFieldByNameCreate("PROYECTO_ID").getStringValue());
			proveedoresLoan.setEstado(detail.findFieldByNameCreate("ESTADO").getStringValue());
			proveedoresLoan.setUsuarioCreacion(detail.findFieldByNameCreate("USUARIO_CREACION").getStringValue());
			proveedoresLoan.setFechaCreacion(detail.findFieldByNameCreate("FECHA_CREACION").getStringValue());
			proveedoresLoan.setUsuarioModificacion(detail.findFieldByNameCreate("USUARIO_MODIFICACION").getStringValue());
			proveedoresLoan.setFechaModificacion(detail.findFieldByNameCreate("FECHA_MODIFICACION").getStringValue());
			proveedoresLoanList.add(proveedoresLoan);
		}
		return proveedoresLoanList;
	}

	public List<ProveedoresLoan> getProveedoresByDocOrNom(Optional<String> razon, Optional<String> numDoc) throws Exception {
		log.info("LoanProcessCore - consultaSolicitudByDocOrNom - inicio");

		Detail detail = new Detail();
		List<ProveedoresLoan> proveedoresLoanList =  new ArrayList<>();

		detail.setType(TransactionFitBank.QUERY.getType());
		detail.setSubsystem(TransactionFitBank.QUERY.getSubsystem());
		detail.setTransaction(TransactionFitBank.QUERY.getTransacction());
		detail.setVersion(TransactionFitBank.QUERY.getVersion());

		if(numDoc.isPresent()){
			detail.findFieldByNameCreate("NUMERO_DOCUMENTO").setValue(numDoc.get());
		}
		if(razon.isPresent()){
			detail.findFieldByNameCreate("RAZON_SOCIAL").setValue(razon.get());
		}

		log.info("detail antes prepareHeader: " + detail.toString());
		detailHandler.prepareHeaderDetail(detail);

		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());
		Table table = detail.findTableByAlias("TPOTENCIAL_PROVEEDOR");
		for(Record record: table.getRecords()){
			ProveedoresLoan proveedoresLoan = new ProveedoresLoan();
			proveedoresLoan.setProveedoresId(detail.findFieldByNameCreate("PROVEEDORESID").getStringValue());
			proveedoresLoan.setRazonSocial(detail.findFieldByNameCreate("RAZON_SOCIAL").getStringValue());
			proveedoresLoan.setTipoPersona(detail.findFieldByNameCreate("TIPO_PERSONA").getStringValue());
			proveedoresLoan.setTipoDocumento(detail.findFieldByNameCreate("TIPO_DOCUMENTO").getStringValue());
			proveedoresLoan.setNumeroDocumento(detail.findFieldByNameCreate("NUMERO_DOCUMENTO").getStringValue());
			proveedoresLoan.setCorreo(detail.findFieldByNameCreate("CORREO").getStringValue());
			proveedoresLoan.setTelefono(detail.findFieldByNameCreate("TELEFONO").getStringValue());
			proveedoresLoan.setProyectoId(detail.findFieldByNameCreate("PROYECTO_ID").getStringValue());
			proveedoresLoan.setEstado(detail.findFieldByNameCreate("ESTADO").getStringValue());
			proveedoresLoan.setUsuarioCreacion(detail.findFieldByNameCreate("USUARIO_CREACION").getStringValue());
			proveedoresLoan.setFechaCreacion(detail.findFieldByNameCreate("FECHA_CREACION").getStringValue());
			proveedoresLoan.setUsuarioModificacion(detail.findFieldByNameCreate("USUARIO_MODIFICACION").getStringValue());
			proveedoresLoan.setFechaModificacion(detail.findFieldByNameCreate("FECHA_MODIFICACION").getStringValue());
			proveedoresLoanList.add(proveedoresLoan);
		}
		return proveedoresLoanList;
	}

	public CambioEstadoResponse saveProveedores(ProveedoresLoan request) throws Exception {
		log.info("LoanProcessCore.saveProveedores - inicio");
		CambioEstadoResponse respuesta = new CambioEstadoResponse();
		respuesta.setMensaje("Proveedor guardado correctamente.");
		Detail detail = new Detail();

		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("ACCION").setValue("GUARDAR");
		Table table = new Table("TPOTENCIAL_PROVEEDOR", "POTENCIAL_PROVEEDOR");
		table.setFinancial(false);
		table.setReadonly(true);
		table.setRequestedRecords(1);
		Record record = new Record();

		record.addField(new Field("PROVEEDORESID"));
		record.addField(new Field("RAZON_SOCIAL"));
		record.addField(new Field("TIPO_PERSONA"));
		record.addField(new Field("TIPO_DOCUMENTO"));
		record.addField(new Field("NUMERO_DOCUMENTO"));
		record.addField(new Field("CORREO"));
		record.addField(new Field("TELEFONO"));
		record.addField(new Field("FECHA_CADUCIDAD"));
		record.addField(new Field("PROYECTO_ID"));
		record.addField(new Field("ESTADO"));
		record.addField(new Field("USUARIO_CREACION"));
		record.addField(new Field("FECHA_CREACION"));
		record.addField(new Field("USUARIO_MODIFICACION"));
		record.addField(new Field("FECHA_MODIFICACION"));

		record.setValue("PROVEEDORESID", request.getProveedoresId());
		record.setValue("RAZON_SOCIAL", request.getRazonSocial());
		record.setValue("TIPO_PERSONA", request.getTipoPersona());
		record.setValue("TIPO_DOCUMENTO", request.getTipoDocumento());
		record.setValue("NUMERO_DOCUMENTO", request.getNumeroDocumento());
		record.setValue("CORREO", request.getCorreo());
		record.setValue("TELEFONO", request.getTelefono());
		record.setValue("FECHA_CADUCIDAD", request.getFechaCaducidad());
		record.setValue("PROYECTO_ID", request.getProyectoId());
		record.setValue("ESTADO", request.getEstado());
		record.setValue("USUARIO_CREACION", request.getUsuarioCreacion());
		record.setValue("FECHA_CREACION", new Date());

		table.addRecord(record);
		detail.addTable(table);

		detailHandler.prepareHeaderDetail(detail);
		log.info("detail después prepareHeader: " + detail.toString());
		try{
			detail = process(detail);
		}catch (Exception e){
			respuesta.setMensaje("Error al guardar Proveedor.");
			respuesta.setMessage(detail.getResponse().toString());
		}
		log.info("detail después process: " + detail.toString());

		return respuesta;
	}

	public CambioEstadoResponse updateProveedor(ProveedoresLoan request){
		log.info("LoanProcessCore.updateProveedor - inicio");
		CambioEstadoResponse respuesta = new CambioEstadoResponse();
		respuesta.setMensaje("Proveedor actualizado correctamente.");
		Detail detail = new Detail();

		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("ACCION").setValue("ACTUALIZAR");
		Table table = new Table("TPOTENCIAL_PROVEEDOR", "POTENCIAL_PROVEEDOR");
		table.setFinancial(false);
		table.setReadonly(true);
		table.setRequestedRecords(1);
		Record record = new Record();

		record.addField(new Field("PROVEEDORESID"));
		record.addField(new Field("RAZON_SOCIAL"));
		record.addField(new Field("TIPO_PERSONA"));
		record.addField(new Field("TIPO_DOCUMENTO"));
		record.addField(new Field("NUMERO_DOCUMENTO"));
		record.addField(new Field("CORREO"));
		record.addField(new Field("TELEFONO"));
		record.addField(new Field("FECHA_CADUCIDAD"));
		record.addField(new Field("PROYECTO_ID"));
		record.addField(new Field("ESTADO"));
		record.addField(new Field("USUARIO_CREACION"));
		record.addField(new Field("FECHA_CREACION"));
		record.addField(new Field("USUARIO_MODIFICACION"));
		record.addField(new Field("FECHA_MODIFICACION"));

		record.setValue("PROVEEDORESID", request.getProveedoresId());
		record.setValue("RAZON_SOCIAL", request.getRazonSocial());
		record.setValue("TIPO_PERSONA", request.getTipoPersona());
		record.setValue("TIPO_DOCUMENTO", request.getTipoDocumento());
		record.setValue("NUMERO_DOCUMENTO", request.getNumeroDocumento());
		record.setValue("CORREO", request.getCorreo());
		record.setValue("TELEFONO", request.getTelefono());
		record.setValue("FECHA_CADUCIDAD", request.getFechaCaducidad());
		record.setValue("PROYECTO_ID", request.getProyectoId());
		record.setValue("ESTADO", request.getEstado());
		record.setValue("USUARIO_CREACION", request.getUsuarioCreacion());
		record.setValue("FECHA_CREACION", new Date());

		table.addRecord(record);
		detail.addTable(table);

		detailHandler.prepareHeaderDetail(detail);
		log.info("detail después prepareHeader: " + detail.toString());
		try{
			detail = process(detail);
		}catch (Exception e){
			respuesta.setMensaje("Error al guardar Proveedor.");
			respuesta.setMessage(detail.getResponse().toString());
		}
		log.info("detail después process: " + detail.toString());

		return respuesta;
	}

	public CambioEstadoResponse saveSolicitud(SolicitudProveedoresRequest request) throws Exception {
		log.info("LoanProcessCore.InsertarSolicitud - inicio");
		CambioEstadoResponse respuesta = new CambioEstadoResponse();
		Detail detail = new Detail();

		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("METODO_MANTENIMIENTO").setValue("INSERT");

		Table t7 = new Table("TSOLICITUD_PROVEEDOR", "SOLICITUD_PROVEEDOR");
		t7.setFinancial(false);
		t7.setReadonly(true);
		t7.setRequestedRecords(-1);
		Record record = new Record();

		record.addField(new Field("NUM_SOLICITUD_PROVEEDOR"));
		record.addField(new Field("RAZON_SOCIAL"));
		record.addField(new Field("RUC"));
		record.addField(new Field("ACTIVIDADECONOMICAPRINC"));
		record.addField(new Field("ACTIVIDADECONOMICASEC"));
		record.addField(new Field("TIPO_DOCUMENTO"));
		record.addField(new Field("NUMERO_DOCUMENTO"));
		record.addField(new Field("PROGRAMA_ANTICORRUPCION"));
		record.addField(new Field("NOMBRE_ENCARGADO_PREVEN"));
		record.addField(new Field("LICITA_ESTADO"));
		record.addField(new Field("DIRECCION"));
		record.addField(new Field("UBIGEO"));
		record.addField(new Field("CORREO"));
		record.addField(new Field("TELEFONO"));
		record.addField(new Field("ANEXO"));
		record.addField(new Field("ANIOS_MERCADO"));
		record.addField(new Field("DIRECTOR_ID"));
		record.addField(new Field("ACCIONISTA_ID"));
		record.addField(new Field("PRODUCTO_ID"));
		record.addField(new Field("ESTADO"));
		record.addField(new Field("USUARIO_CREACION"));
		record.addField(new Field("FECHA_CREACION"));
		record.addField(new Field("USUARIO_MODIFICACION"));
		record.addField(new Field("FECHA_MODIFICACION"));

		record.setValue("NUM_SOLICITUD_PROVEEDOR", request.getSolicitudProveedoresId());
		record.setValue("RAZON_SOCIAL", request.getRazonSocial());
		record.setValue("RUC", request.getRuc());
		record.setValue("ACTIVIDADECONOMICAPRINC", request.getActividadEconomicaPrinc());
		record.setValue("ACTIVIDADECONOMICASEC", request.getActividadEconomicaSec());
		record.setValue("TIPO_DOCUMENTO", request.getTipoDocumento());
		record.setValue("NUMERO_DOCUMENTO", request.getNumeroDocumento());
		record.setValue("PROGRAMA_ANTICORRUPCION", request.getProgramaAnticorrupcion());
		record.setValue("NOMBRE_ENCARGADO_PREVEN", request.getNombreEncargadoPreven());
		record.setValue("LICITA_ESTADO", request.getLicitaEstado());
		record.setValue("DIRECCION", request.getDireccion());
		record.setValue("UBIGEO", request.getUbigeo());
		record.setValue("CORREO", request.getCorreo());
		record.setValue("TELEFONO", request.getTelefono());
		record.setValue("ANEXO", request.getAnexo());
		record.setValue("ANIOS_MERCADO", request.getAniosMercado());
		record.setValue("DIRECTOR_ID", request.getDirectoresRequest().getDirectorId());
		record.setValue("ACCIONISTA_ID", request.getAccionistaRequest().getAccionistaId());
		record.setValue("PRODUCTO_ID", request.getProductosRequest().getProductoId());
		record.setValue("ESTADO", request.getEstado());
		record.setValue("USUARIO_CREACION", request.getUsuarioCreacion());
		record.setValue("FECHA_CREACION", request.getFechaCreacion());
		record.setValue("USUARIO_MODIFICACION", request.getUsuarioModificacion());
		record.setValue("FECHA_MODIFICACION", request.getFechaModificacion());

		t7.addRecord(record);
		detail.addTable(t7);

		detailHandler.prepareHeaderDetail(detail);
		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());

		respuesta.setMensaje("Datos Insertados");
		return respuesta;
	}

	public DirectoresResponse saveSolicitudDirectores(SolicitudProveedoresRequest request) throws Exception {
		log.info("LoanProcessCore.saveSolicitudDirectores - inicio");
		DirectoresResponse respuesta = new DirectoresResponse();
		Detail detail = new Detail();

		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("METODO_MANTENIMIENTO").setValue("INSERT");

		Table t7 = new Table("TDIRECTORES", "DIRECTORES");
		t7.setFinancial(false);
		t7.setReadonly(true);
		t7.setRequestedRecords(-1);
		Record record = new Record();

		record.addField(new Field("DIRECTORES_ID"));
		record.addField(new Field("NOMBRES"));
		record.addField(new Field("APELLIDOS"));
		record.addField(new Field("TIPO_DOCUMENTO"));
		record.addField(new Field("NUMERO_DOCUMENTO"));
		record.addField(new Field("NACIONALIDAD"));
		record.addField(new Field("ESTADO"));
		record.addField(new Field("USUARIO_CREACION"));
		record.addField(new Field("FECHA_CREACION"));
		record.addField(new Field("USUARIO_MODIFICACION"));
		record.addField(new Field("FECHA_MODIFICACION"));

		record.setValue("DIRECTORES_ID", request.getDirectoresRequest().getDirectorId());
		record.setValue("NOMBRES", request.getDirectoresRequest().getNombre());
		record.setValue("APELLIDOS", request.getDirectoresRequest().getApellido());
		record.setValue("TIPO_DOCUMENTO", request.getDirectoresRequest().getTipoDocumento());
		record.setValue("NUMERO_DOCUMENTO", request.getDirectoresRequest().getNumeroDocumento());
		record.setValue("NACIONALIDAD", request.getDirectoresRequest().getNacionalidad());
		record.setValue("ESTADO", request.getEstado());
		record.setValue("USUARIO_CREACION", request.getUsuarioCreacion());
		record.setValue("FECHA_CREACION", request.getFechaCreacion());
		record.setValue("USUARIO_MODIFICACION", request.getUsuarioModificacion());
		record.setValue("FECHA_MODIFICACION", request.getFechaModificacion());

		t7.addRecord(record);
		detail.addTable(t7);

		detailHandler.prepareHeaderDetail(detail);
		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());

		respuesta.setDirectorId(detail.findFieldByNameCreate("DIRECTORES_ID").getStringValue());
		respuesta.setNombre(detail.findFieldByNameCreate("NOMBRES").getStringValue());
		respuesta.setApellido(detail.findFieldByNameCreate("APELLIDOS").getStringValue());
		respuesta.setTipoDocumento(detail.findFieldByNameCreate("TIPO_DOCUMENTO").getStringValue());
		respuesta.setNumeroDocumento(detail.findFieldByNameCreate("NUMERO_DOCUMENTO").getStringValue());
		respuesta.setNacionalidad(detail.findFieldByNameCreate("NACIONALIDAD").getStringValue());
		return respuesta;
	}
	public ProductosResponse saveSolicitudProductos(SolicitudProveedoresRequest request) throws Exception {
		log.info("LoanProcessCore.saveSolicitudProductos - inicio");
		ProductosResponse respuesta = new ProductosResponse();
		Detail detail = new Detail();

		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("METODO_MANTENIMIENTO").setValue("INSERT");

		Table t7 = new Table("TPRODUCTOS", "PRODUCTOS");
		t7.setFinancial(false);
		t7.setReadonly(true);
		t7.setRequestedRecords(-1);
		Record record = new Record();

		record.addField(new Field("PRODUCTO_ID"));
		record.addField(new Field("NOMBRE_PRODUCTO"));
		record.addField(new Field("RUBRO"));
		record.addField(new Field("SECTOR"));
		record.addField(new Field("ESTADO"));
		record.addField(new Field("USUARIO_CREACION"));
		record.addField(new Field("FECHA_CREACION"));
		record.addField(new Field("USUARIO_MODIFICACION"));
		record.addField(new Field("FECHA_MODIFICACION"));

		record.setValue("PRODUCTO_ID", request.getProductosRequest().getProductoId());
		record.setValue("NOMBRE_PRODUCTO", request.getProductosRequest().getNombreProducto());
		record.setValue("RUBRO", request.getProductosRequest().getRubro());
		record.setValue("SECTOR", request.getProductosRequest().getSector());
		record.setValue("ESTADO", request.getEstado());
		record.setValue("USUARIO_CREACION", request.getUsuarioCreacion());
		record.setValue("FECHA_CREACION", request.getFechaCreacion());
		record.setValue("USUARIO_MODIFICACION", request.getUsuarioModificacion());
		record.setValue("FECHA_MODIFICACION", request.getFechaModificacion());

		t7.addRecord(record);
		detail.addTable(t7);

		detailHandler.prepareHeaderDetail(detail);
		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());

		respuesta.setProductoId(detail.findFieldByNameCreate("PRODUCTO_ID").getStringValue());
		respuesta.setNombreProducto(detail.findFieldByNameCreate("NOMBRE_PRODUCTO").getStringValue());
		respuesta.setRubro(detail.findFieldByNameCreate("RUBRO").getStringValue());
		respuesta.setSector(detail.findFieldByNameCreate("SECTOR").getStringValue());
		return respuesta;
	}
	public AccionistaResponse saveSolicitudAccionista(SolicitudProveedoresRequest request) throws Exception {
		log.info("LoanProcessCore.saveSolicitudAccionista - inicio");
		AccionistaResponse respuesta = new AccionistaResponse();
		Detail detail = new Detail();

		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("METODO_MANTENIMIENTO").setValue("INSERT");

		Table t7 = new Table("TACCIONISTAS", "ACCIONISTAS");
		t7.setFinancial(false);
		t7.setReadonly(true);
		t7.setRequestedRecords(-1);
		Record record = new Record();

		record.addField(new Field("ACCIONISTAS_ID"));
		record.addField(new Field("NOMBRES"));
		record.addField(new Field("APELLIDOS"));
		record.addField(new Field("CAPITAL"));
		record.addField(new Field("NACIONALIDAD"));
		record.addField(new Field("ESTADO"));
		record.addField(new Field("USUARIO_CREACION"));
		record.addField(new Field("FECHA_CREACION"));
		record.addField(new Field("USUARIO_MODIFICACION"));
		record.addField(new Field("FECHA_MODIFICACION"));

		record.setValue("ACCIONISTAS_ID", request.getAccionistaRequest().getAccionistaId());
		record.setValue("NOMBRES", request.getAccionistaRequest().getNombre());
		record.setValue("APELLIDOS", request.getAccionistaRequest().getApellido());
		record.setValue("CAPITAL", request.getAccionistaRequest().getCapital());
		record.setValue("NACIONALIDAD", request.getAccionistaRequest().getNacionalidad());
		record.setValue("ESTADO", request.getEstado());
		record.setValue("USUARIO_CREACION", request.getUsuarioCreacion());
		record.setValue("FECHA_CREACION", request.getFechaCreacion());
		record.setValue("USUARIO_MODIFICACION", request.getUsuarioModificacion());
		record.setValue("FECHA_MODIFICACION", request.getFechaModificacion());

		t7.addRecord(record);
		detail.addTable(t7);

		detailHandler.prepareHeaderDetail(detail);
		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());

		respuesta.setAccionistaId(detail.findFieldByNameCreate("ACCIONISTAS_ID").getStringValue());
		respuesta.setNombre(detail.findFieldByNameCreate("NOMBRES").getStringValue());
		respuesta.setApellido(detail.findFieldByNameCreate("APELLIDOS").getStringValue());
		respuesta.setCapital(detail.findFieldByNameCreate("CAPITAL").getStringValue());
		respuesta.setNacionalidad(detail.findFieldByNameCreate("NACIONALIDAD").getStringValue());
		return respuesta;
	}

	public GerenteResponse saveSolicitudGerente(SolicitudProveedoresRequest request) throws Exception {
		log.info("LoanProcessCore.saveSolicitudGerente - inicio");
		GerenteResponse respuesta = new GerenteResponse();
		Detail detail = new Detail();

		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("METODO_MANTENIMIENTO").setValue("INSERT");

		Table t7 = new Table("TGERENTES", "TGERENTES");
		t7.setFinancial(false);
		t7.setReadonly(true);
		t7.setRequestedRecords(-1);
		Record record = new Record();

		record.addField(new Field("GERENTE_ID"));
		record.addField(new Field("NOMBRES"));
		record.addField(new Field("APELLIDOS"));
		record.addField(new Field("TIPO_DOCUMENTO"));
		record.addField(new Field("NUMERO_DOCUMENTO"));
		record.addField(new Field("NACIONALIDAD"));
		record.addField(new Field("ESTADO"));
		record.addField(new Field("USUARIO_CREACION"));
		record.addField(new Field("FECHA_CREACION"));
		record.addField(new Field("USUARIO_MODIFICACION"));
		record.addField(new Field("FECHA_MODIFICACION"));


		record.setValue("GERENTE_ID", request.getGerenteRequest().getGerenteId());
		record.setValue("NOMBRES", request.getGerenteRequest().getNombres());
		record.setValue("APELLIDOS", request.getGerenteRequest().getApellidos());
		record.setValue("TIPO_DOCUMENTO", request.getGerenteRequest().getTipoDocumento());
		record.setValue("NUMERO_DOCUMENTO", request.getGerenteRequest().getNumeroDocumento());
		record.setValue("NACIONALIDAD", request.getGerenteRequest().getNacionalidad());
		record.setValue("ESTADO", request.getEstado());
		record.setValue("USUARIO_CREACION", request.getUsuarioCreacion());
		record.setValue("FECHA_CREACION", request.getFechaCreacion());
		record.setValue("USUARIO_MODIFICACION", request.getUsuarioModificacion());
		record.setValue("FECHA_MODIFICACION", request.getFechaModificacion());

		t7.addRecord(record);
		detail.addTable(t7);

		detailHandler.prepareHeaderDetail(detail);
		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());

		respuesta.setGerenteId(detail.findFieldByNameCreate("GERENTE_ID").getStringValue());
		respuesta.setNombres(detail.findFieldByNameCreate("NOMBRES").getStringValue());
		respuesta.setApellidos(detail.findFieldByNameCreate("APELLIDOS").getStringValue());
		respuesta.setTipoDocumento(detail.findFieldByNameCreate("TIPO_DOCUMENTO").getStringValue());
		respuesta.setNumeroDocumento(detail.findFieldByNameCreate("NUMERO_DOCUMENTO").getStringValue());
		respuesta.setNacionalidad(detail.findFieldByNameCreate("NACIONALIDAD").getStringValue());
		return respuesta;
	}



	
	/*public CambioEstadoResponse cambioEstado(String numSolicitud,
											 String numDNIservicio, String estadoSolicitud) throws Exception {
		log.info("LoanProcessCore.reprograma - inicio");
		CambioEstadoResponse respuesta = new CambioEstadoResponse();
		
		
		Detail detail = new Detail();
		
		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("METODO_MANTENIMIENTO").setValue("UPDATE");
		
		Table t7 = new Table("TSOLICITUDE_UPDATE", "SOLICITUDE_UPDATE");
		t7.setFinancial(false);
		t7.setReadonly(true);
		t7.setRequestedRecords(-1);
		Record record = new Record();

		record.addField(new Field("NUMSOLICITUD"));
		record.addField(new Field("NUMERO_DNI"));
		record.addField(new Field("ESTADO_SOLICITUD"));
		
		record.setValue("NUMSOLICITUD", numSolicitud);
		record.setValue("NUMERO_DNI", numDNIservicio);
		record.setValue("ESTADO_SOLICITUD", estadoSolicitud);
		
		t7.addRecord(record);
		detail.addTable(t7);		
		
		detailHandler.prepareHeaderDetail(detail);		
		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());

		respuesta.setMensaje("Cambio de estado realizado");
		return respuesta;	
		}*/
	

	
	
	
	/**public IngresarSolicitudResponse ingresarSolicitud(String numSolicitud,
								String numDNIservicio, String estadoSolicitud) throws Exception {
		log.info("LoanProcessCore.ingresarSolicitud - inicio");
		IngresarSolicitudResponse respuesta = new IngresarSolicitudResponse();


		Detail detail = new Detail();

		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("METODO_MANTENIMIENTO").setValue("UPDATE_ACEPTADA");

		Table t7 = new Table("TSOLICITUDE_UPDATE", "SOLICITUDE_UPDATE");
		t7.setFinancial(false);
		t7.setReadonly(true);
		t7.setRequestedRecords(-1);
		Record record = new Record();

		record.addField(new Field("NUMSOLICITUD"));
		record.addField(new Field("NUMERO_DNI"));
		record.addField(new Field("ESTADO_SOLICITUD"));

		record.setValue("NUMSOLICITUD", numSolicitud);
		record.setValue("NUMERO_DNI", numDNIservicio);
		record.setValue("ESTADO_SOLICITUD", estadoSolicitud);

		t7.addRecord(record);
		detail.addTable(t7);

		detailHandler.prepareHeaderDetail(detail);
		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());

		respuesta.setNomProductoServico(detail.findFieldByName("NOMPRODUCTOSERVICIO").getStringValue());
		respuesta.setMensaje("Cambio de estado realizado");
		return respuesta;
		}






	public SolicitudAceptadaResponse solicitudAceptada(String numSolicitud,
								String numDNIservicio, String estadoSolicitud) throws Exception {
		log.info("LoanProcessCore.cambioEstadoAceptado - inicio");
		SolicitudAceptadaResponse respuesta = new SolicitudAceptadaResponse();


		Detail detail = new Detail();

		detail.setType(TransactionFitBank.MANTENIMIENTO.getType());
		detail.setSubsystem(TransactionFitBank.MANTENIMIENTO.getSubsystem());
		detail.setTransaction(TransactionFitBank.MANTENIMIENTO.getTransacction());
		detail.setVersion(TransactionFitBank.MANTENIMIENTO.getVersion());

		detail.findFieldByNameCreate("METODO_MANTENIMIENTO").setValue("UPDATE");

		Table t7 = new Table("TSOLICITUDE_UPDATE", "SOLICITUDE_UPDATE");
		t7.setFinancial(false);
		t7.setReadonly(true);
		t7.setRequestedRecords(-1);
		Record record = new Record();

		record.addField(new Field("NUMSOLICITUD"));
		record.addField(new Field("NUMERO_DNI"));
		record.addField(new Field("ESTADO_SOLICITUD"));

		record.setValue("NUMSOLICITUD", numSolicitud);
		record.setValue("NUMERO_DNI", numDNIservicio);
		record.setValue("ESTADO_SOLICITUD", estadoSolicitud);

		t7.addRecord(record);
		detail.addTable(t7);

		detailHandler.prepareHeaderDetail(detail);
		log.info("detail después prepareHeader: " + detail.toString());
		detail = process(detail);
		log.info("detail después process: " + detail.toString());

		respuesta.setCodigoProducto(detail.findFieldByName("ESTADOSOLICITUD").getStringValue());

		// nuevo response:  Código del producto.

		return respuesta;
		}**/
	
	public Detail process(Detail detail) throws Exception {
		detail.findFieldByNameCreate("SERVICIO").setValue("proveedores");
		val xmlRequest = detail.toXml().toString();
		log.info("detail después prepareHeader: " + xmlRequest);
		val xmlResponse = uciClient.sendSerializable2(xmlRequest).toString();
		log.info("detail después process: " + xmlResponse);
		MetodosUtiles.verificarGRS(xmlResponse);
		Detail detailResponse = Detail.valueOf(xmlResponse);
		
		return detailResponse;
	}
}
