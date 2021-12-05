package com.unab.tienda_a_la_mano.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import com.unab.tienda_a_la_mano.entity.PedidoEntity;


public interface IPedidoService {
	
	//Listar todos
	public List<PedidoEntity> all();
	
	//Listar por ID
	public Optional<PedidoEntity> findById(Long id);
	
		
	//Guardar-actualizar
	public PedidoEntity save(PedidoEntity pedidoEntity);	

	//Eliminar por ID
	public void deleteById(Long id);
	
	//REQ 10
	public Long traerPuntos( Long idpedido );
	
	//REQ 11
	public String traerEstado( int idpedido );
	
	//REQ 5
	public Map<String, Object> valoresPedido( Long idpedido );
	
	//REQ 21
	//public List<Map<String, Object>> pedidosAnteriores( Long idcliente );
	
	//
	//Listar Activos
	public List<PedidoEntity> seleccionarxEstado(String filtro );
}
