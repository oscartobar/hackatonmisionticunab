package com.unab.tienda_a_la_mano.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unab.tienda_a_la_mano.entity.PedidoEntity;
import com.unab.tienda_a_la_mano.entity.ProductoEntity;
 
public interface IPedidoRepository extends JpaRepository<PedidoEntity, Long>{
	// AQUI SE PONEN LAS CONSULTAS ADICIONALES A LA BD
	
	//Traer el estado de un pedido
	@Query(value="select estado from pedidos where id = ?1",  nativeQuery = true)
	String traerEstado( int idpedido );
	
	//Traer puntos de un pedido
	@Query(value="select sum(puntos) from detalle_pedidos where pedido_id = ?1",  nativeQuery = true)
	Long traerPuntos( Long idpedido );
	
	//pedido antiguos
	@Query(value = "SELECT * FROM pedidos where cliente_id = ?1")
	List<PedidoEntity> buscarantiguos(Long cliente);
	
	
	
}
