package com.unab.tienda_a_la_mano.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unab.tienda_a_la_mano.entity.PedidoEntity;
 
public interface IPedidoRepository extends JpaRepository<PedidoEntity, Long>{
	// AQUI SE PONEN LAS CONSULTAS ADICIONALES A LA BD
	
	//Traer el estado de un pedido
	@Query(value="select estado from pedidos where id = ?1",  nativeQuery = true)
	String traerEstado( int idpedido );
	
	//Traer puntos de un pedido
	@Query(value="select sum(puntos) from detalle_pedidos where pedido_id = ?1",  nativeQuery = true)
	Long traerPuntos( Long idpedido );
	
	
}
