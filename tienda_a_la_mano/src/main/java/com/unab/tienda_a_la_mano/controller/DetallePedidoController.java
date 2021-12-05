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

import com.unab.tienda_a_la_mano.entity.DetallePedidoEntity;
import com.unab.tienda_a_la_mano.entity.PedidoEntity;
import com.unab.tienda_a_la_mano.entity.TiendaEntity;
import com.unab.tienda_a_la_mano.service.IDetallePedidoService;

import com.unab.tienda_a_la_mano.service.ITiendaService;
import com.unab.tienda_a_la_mano.serviceImplement.TiendaService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/detallepedido")
public class DetallePedidoController {
	@Autowired
	private IDetallePedidoService service;
	
	// Con el metodo GET sin parametros consultamos todos los registros
		@GetMapping
		public List<DetallePedidoEntity> all() {
			return service.all();
		}

		// Con el metodo GET con parametros consultamos los registros por ID
		@GetMapping("{id}")
		public Optional<DetallePedidoEntity> show(@PathVariable Long id) {
			return service.findById(id);
		}
					
		// Con el metodo POST creamos los registros
		@PostMapping
		@ResponseStatus(code = HttpStatus.CREATED)
		public DetallePedidoEntity save(@RequestBody DetallePedidoEntity entidad) {
			return service.save(entidad);
		}


		// Con el metodo PUT actualizamos los registros por ID
		@PutMapping("{id}")
		@ResponseStatus(code = HttpStatus.CREATED)
		public DetallePedidoEntity update(@PathVariable Long id, @RequestBody DetallePedidoEntity entidad) {
			Optional<DetallePedidoEntity> op = service.findById(id);

			if (!op.isEmpty()) {
				DetallePedidoEntity tabla = op.get();
				// actualizar cada propiedad

				
				tabla.setImpuesto(entidad.getImpuesto());
				tabla.setPedido_id(entidad.getPedido_id());
				tabla.setPrecio(entidad.getPrecio());
				tabla.setDescuento(entidad.getDescuento());
				tabla.setProducto_id(entidad.getProducto_id());
				tabla.setPuntos(entidad.getPuntos());
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
		
		
		// REQ 4 disminuir cantidad
		@PutMapping("/disminuir/{id}")
		public ResponseEntity<?> actualizaTipoEntrega(@PathVariable Long id, @RequestParam("cantidad") double mensaje) {
			Map<String, Object> respuesta = new HashMap<>();
			try {
				Optional<DetallePedidoEntity> op = service.findById(id);

				if (!op.isEmpty()) {
					DetallePedidoEntity tabla = op.get();
					// actualizar cada propiedad

					tabla.setCantidad(mensaje);

					service.save(tabla);
				}
			} catch (DataAccessException e) {
				respuesta.put("No actualizo", "Paila");
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_ACCEPTABLE);
			}
			respuesta.put("Actualizado", "Se asigno la cantidad");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
		}
		
		

		
}
