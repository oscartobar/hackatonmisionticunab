package com.unab.tienda_a_la_mano.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unab.tienda_a_la_mano.entity.DomiciliarioEntity;
import com.unab.tienda_a_la_mano.entity.PedidoEntity;
import com.unab.tienda_a_la_mano.entity.TiendaEntity;
import com.unab.tienda_a_la_mano.service.IDetallePedidoService;
import com.unab.tienda_a_la_mano.service.IDomiciliarioService;
import com.unab.tienda_a_la_mano.service.IPedidoService;
import com.unab.tienda_a_la_mano.service.ITiendaService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/pedido")
public class PedidoController {

	// Accedemos a los metodos de la interface 
	@Autowired
	private IPedidoService service;
	
	@Autowired
	private ITiendaService serviceTienda;
	
	@Autowired
	private IDomiciliarioService serviceDom;


	@Autowired
	private IDetallePedidoService serviceDetallePedido;

	// Con el metodo GET sin parametros consultamos todos los registros
	@GetMapping
	public List<PedidoEntity> all() {
		return service.all();
	}

	// Con el metodo GET con parametros consultamos los registros por ID
	@GetMapping("{id}")
	public Optional<PedidoEntity> show(@PathVariable Long id) {
		return service.findById(id);
	}
				
	// Con el metodo POST creamos los registros
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PedidoEntity save(@RequestBody PedidoEntity entidad) {
		return service.save(entidad);
	}


	// Con el metodo PUT actualizamos los registros por ID
	@PutMapping("{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PedidoEntity update(@PathVariable Long id, @RequestBody PedidoEntity entidad) {
		Optional<PedidoEntity> op = service.findById(id);

		if (!op.isEmpty()) {
			PedidoEntity tabla = op.get();
			// actualizar cada propiedad

			tabla.setCalificacion(entidad.getCalificacion());
			tabla.setFecha(entidad.getFecha());
			tabla.setCosto_envio(entidad.getCosto_envio());
			tabla.setPago_entrega(entidad.getPago_entrega());
			tabla.setCliente(entidad.getCliente());
			tabla.setDomiciliario(entidad.getDomiciliario());
			tabla.setEstado(entidad.getEstado());
			tabla.setObservacion(entidad.getObservacion());
			tabla.setPago_entrega(entidad.getPago_entrega());
			tabla.setRango_entrega(entidad.getRango_entrega());
			tabla.setTienda(entidad.getTienda());
			tabla.setTipo_entrega(entidad.getTipo_entrega());
			tabla.setTotal_descuento(entidad.getTotal_descuento());
			tabla.setTotal_impuesto(entidad.getTotal_impuesto());
			tabla.setTotal_pedido(entidad.getTotal_pedido());

			return service.save(tabla);
		}

		return entidad;
	}

	// Con el metodo DELETE eliminamos los registros por ID
	@DeleteMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		service.deleteById(id);
	}
	
	
	///////////////////////////
	//	 REQUERIMIENTOS		///
	///////////////////////////
	


	

	// REQ 5 Consulta los valores de un pedido 
	@GetMapping("/consultas/valores")
	public Map<String, Object> vervalores(@RequestParam("id") Long id ) {
		return service.valoresPedido(id);
	}

	// REQ 6 Poner tipo de entrega
		@PutMapping("/entrega/{id}")
		public ResponseEntity<?> actualizaTipoEntrega(@PathVariable Long id, @RequestParam("tipoentrega") String mensaje) {
			Map<String, Object> respuesta = new HashMap<>();
			try {
				Optional<PedidoEntity> op = service.findById(id);

				if (!op.isEmpty()) {
					PedidoEntity tabla = op.get();
					// actualizar cada propiedad

					tabla.setTipo_entrega(mensaje);

					service.save(tabla);
				}
			} catch (DataAccessException e) {
				respuesta.put("No actualizo", "Paila");
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
			}
			respuesta.put("Actualizado", "Se asigno el tipo de entrega");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
		}

		// REQ 10 Enviar pedido
		@PutMapping("/entregar/{id}")
		public ResponseEntity<?> enviarPedido(@PathVariable Long id) {
			PedidoEntity tabla;
			Map<String, Object> respuesta = new HashMap<>();
			try {
				Optional<PedidoEntity> op = service.findById(id);

				if (!op.isEmpty()) {
					tabla = op.get();
					// actualizar cada propiedad

					tabla.setEstado("ENVIADO");

					service.save(tabla);
				}
			} catch (DataAccessException e) {
				respuesta.put("No actualizo", "Paila");
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
			}
			respuesta.put("Enviado", "Ud gano "+ String.valueOf( service.traerPuntos(id) ) +" puntos por este pedido");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
		}
		
				
	// REQ 11  ver estado del pedido
	@GetMapping("/consultas/verestado/{id}")
	public String mostrarEstado(@PathVariable int id) {
		return service.traerEstado(id);
	}
	
	// REQ 11  ESTE SIRVE CON PARAMETROS 
	@GetMapping("/consultas/verestado2")
	public String mostrarEstado2(@RequestParam("id") int id ) {
		return service.traerEstado(id);
	}

	// REQ 12  Cancelar pedido
	@PutMapping("/cancelar/{id}")
	public String cancelar(@PathVariable Long id ,@RequestParam("estado") String mensaje) {
		
		try {
			Optional<PedidoEntity> op = service.findById(id);

			if (!op.isEmpty()) {
				PedidoEntity tabla = op.get();
				
				if (!tabla.getEstado().equals("ENVIADO"))
				{
					tabla.setEstado(mensaje);

					service.save(tabla);
				}
				else
				{
					return "NO SE PUEDE CAMBIAR EL ESTADO. EL PEDIDO YA SE ENVIO";
				}
			}
		} catch (DataAccessException e) {
			return "ERROR AL CAMBIAR DE ESTADO";
		}
		
		return "SE ACTUALIZO EL ESTADO A "+mensaje;
	}

	
	// REQ 13 hacer un pedido igual a otro
		@PutMapping("/copiar/{id}")
		@ResponseStatus(code = HttpStatus.CREATED)
		public PedidoEntity copiarpedido(@PathVariable Long id) {
			PedidoEntity tabla = new PedidoEntity();
			
			Optional<PedidoEntity> op = service.findById(id);

			if (!op.isEmpty()) {
				PedidoEntity entidad = op.get();
				// actualizar cada propiedad

				tabla.setCalificacion(entidad.getCalificacion());
				tabla.setFecha(entidad.getFecha());
				tabla.setCosto_envio(entidad.getCosto_envio());
				tabla.setPago_entrega(entidad.getPago_entrega());
				tabla.setCliente(entidad.getCliente());
				tabla.setDomiciliario(entidad.getDomiciliario());
				tabla.setEstado(entidad.getEstado());
				tabla.setObservacion(entidad.getObservacion());
				tabla.setPago_entrega(entidad.getPago_entrega());
				tabla.setRango_entrega(entidad.getRango_entrega());
				tabla.setTienda(entidad.getTienda());
				tabla.setTipo_entrega(entidad.getTipo_entrega());
				tabla.setTotal_descuento(entidad.getTotal_descuento());
				tabla.setTotal_impuesto(entidad.getTotal_impuesto());
				tabla.setTotal_pedido(entidad.getTotal_pedido());
				
				
				PedidoEntity tablarta =  service.save(tabla);
				serviceDetallePedido.duplicar(id,tablarta.getId());
				return tablarta;
			}

			return tabla;
		}
		
	// REQ 15 Poner observacion al pedido
	@PutMapping("/notas/{id}")
	public ResponseEntity<?> actualiza(@PathVariable Long id, @RequestParam String mensaje) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			Optional<PedidoEntity> op = service.findById(id);

			if (!op.isEmpty()) {
				PedidoEntity tabla = op.get();
				// actualizar cada propiedad

				tabla.setObservacion(mensaje);

				service.save(tabla);
			}
		} catch (DataAccessException e) {
			respuesta.put("No actualizo", "Paila");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
		}
		respuesta.put("Actualizado", "Se asigno el mensaje");
		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
	}

	
	// REQ 47 Poner domiciliario al pedido
	@PutMapping("/domi/{id}")
	public ResponseEntity<?> actualizadomi(@PathVariable Long id, @RequestParam("domiciliario") Long mensaje) {
		Map<String, Object> respuesta = new HashMap<>();
		
		Optional<DomiciliarioEntity> opdomiciliario = serviceDom.findById(mensaje);

		
		try {
			Optional<PedidoEntity> op = service.findById(id);

			if (!op.isEmpty()) {
				PedidoEntity tabla = op.get();
				// actualizar cada propiedad

				tabla.setDomiciliario(opdomiciliario.get() );

				service.save(tabla);
			}
		} catch (DataAccessException e) {
			respuesta.put("No actualizo", "Paila");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
		}
		respuesta.put("Actualizado", "Se asigno el domiciliario");
		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
	}
	
	
	//REQ 51 
	@GetMapping("/consultas/activos")
	public List<PedidoEntity> veractivos(@RequestParam("estado") String estado ) {
		return service.seleccionarxEstado(estado);
	}
	
	// REQ 42 Poner tienda al pedido
	@PutMapping("/tienda/{id}")
	public ResponseEntity<?> actualizatienda(@PathVariable Long id, @RequestParam("tienda") Long mensaje) {
		Map<String, Object> respuesta = new HashMap<>();
		
		Optional<TiendaEntity> optienda = serviceTienda.findById(mensaje);

		
		try {
			Optional<PedidoEntity> op = service.findById(id);

			if (!op.isEmpty()) {
				PedidoEntity tabla = op.get();
				// actualizar cada propiedad

				tabla.setTienda(optienda.get());

				service.save(tabla);
			}
		} catch (DataAccessException e) {
			respuesta.put("No actualizo", "Paila");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
		}
		respuesta.put("Actualizado", "Se asigno la tienda");
		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
	}
	
	// REQ 77 Poner tipo de entrega y tienda
			@PutMapping("/entregatienda/{id}")
			public ResponseEntity<?> actualizaTipoEntrega(@PathVariable Long id, @RequestParam("tipoentrega") String mensaje,@RequestParam("tienda") Long tienda) {
				Map<String, Object> respuesta = new HashMap<>();

				try {
					Optional<PedidoEntity> op = service.findById(id);
					Optional<TiendaEntity> optienda = serviceTienda.findById(tienda);

					if (!op.isEmpty()) {
						PedidoEntity tabla = op.get();
						// actualizar cada propiedad
						
						tabla.setTipo_entrega(mensaje);
						tabla.setTienda(optienda.get());
						service.save(tabla);
					}
				} catch (DataAccessException e) {
					respuesta.put("No actualizo", "Paila");
					return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
				}
				respuesta.put("Actualizado", "Se asigno el tipo de entrega");
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
			}


}
