package com.unab.tienda_a_la_mano.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unab.tienda_a_la_mano.entity.PedidoEntity;

public interface IPedidoRepository extends JpaRepository<PedidoEntity, Long>{

	@Query(value="select estado from pedidos where id = ?1",  nativeQuery = true)
	String traerEstado( int idpedido );
}
