package com.unab.tienda_a_la_mano.serviceImplement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unab.tienda_a_la_mano.entity.PedidoEntity;
import com.unab.tienda_a_la_mano.repository.IPedidoRepository;
import com.unab.tienda_a_la_mano.service.IPedidoService;

@Service
public class PedidoService implements IPedidoService{
	
	@Autowired
	private IPedidoRepository repository;

	@Override
	public List<PedidoEntity> all() {
		return this.repository.findAll();
	}

	@Override
	public Optional<PedidoEntity> findById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public PedidoEntity save(PedidoEntity pedidoEntity) {
		return this.repository.save(pedidoEntity);
	}

	@Override
	public void deleteById(Long id) {
		this.repository.deleteById(id);
		
	}

	

	
	// AQUI SE IMPLEMENTA LA LOGICA DE CADA REQUERIMIENTO
	
	//REQ 10
		@Override
		public Long traerPuntos(Long idpedido) {
			
			return repository.traerPuntos(idpedido);
		}
		
	//REQ 11
	@Override
	public String traerEstado(int idpedido) {
		
		return repository.traerEstado(idpedido);
	}
	
	//REQ 5
	@Override
	public Map<String, Object> valoresPedido(Long idpedido) {
		PedidoEntity p = this.repository.findById(idpedido).get();
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("Total Compra", p.getTotal_pedido());
		respuesta.put("Total Descuentos", p.getTotal_descuento());
		respuesta.put("Total Impuestos", p.getTotal_impuesto());
		respuesta.put("Costo Aprox Envio", p.getCosto_envio());
		return respuesta;
	}
	
	//REQ 21
	/*
	@Override
	public List<Map<String, Object>> pedidosAnteriores( Long idcliente ){
		List<PedidoEntity>  p = this.repository.buscarantiguos(idcliente);
		List<Map<String, Object>> lista = new ArrayList<>();
		
		for(PedidoEntity mipedido : p)
		{
			Map<String, Object> respuesta = new HashMap<>();
			respuesta.put("Total Compra", mipedido.getTotal_pedido());
			respuesta.put("Total Descuentos", mipedido.getTotal_descuento());
			respuesta.put("Total Impuestos", mipedido.getTotal_impuesto());
			respuesta.put("Costo Aprox Envio", mipedido.getCosto_envio());
			lista.add(respuesta);
		}
	
	
*/
	

}
