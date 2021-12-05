package com.unab.tienda_a_la_mano.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unab.tienda_a_la_mano.entity.DetallePedidoEntity;
import com.unab.tienda_a_la_mano.entity.ProductoEntity;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedidoEntity, Long>{

	// AQUI SE PONEN LAS CONSULTAS ADICIONALES A LA BD
	
		//duplicar el detalle de un pedido
		@Modifying
		@Query(value="insert into detalle_pedidos SELECT id+22 AS rownum,cantidad,descuento,impuesto,precio,puntos,?2,producto_id FROM detalle_pedidos where pedido_id = ?1",  nativeQuery = true)
		int duplicar( Long idpedidoactual,  Long idpedidonuevo );
		
		@Query(value = "SELECT p FROM ProductoEntity p WHERE p.nombre LIKE %:filtro% OR p.marca.descripcion LIKE %:filtro%")
		List<ProductoEntity> buscarSimilares(@Param("filtro") String filtro);
		
}
