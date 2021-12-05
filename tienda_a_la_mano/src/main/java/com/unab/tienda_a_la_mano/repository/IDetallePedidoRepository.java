package com.unab.tienda_a_la_mano.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.unab.tienda_a_la_mano.entity.DetallePedidoEntity;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedidoEntity, Long>{

	// AQUI SE PONEN LAS CONSULTAS ADICIONALES A LA BD
	
		//duplicar el detalle de un pedido
		@Modifying
		@Query(value="insert into detalle_pedidos SELECT id+22 AS rownum,cantidad,descuento,impuesto,precio,puntos,?2,producto_id FROM detalle_pedidos where pedido_id = ?1",  nativeQuery = true)
		int duplicar( Long idpedidoactual,  Long idpedidonuevo );
		
}
