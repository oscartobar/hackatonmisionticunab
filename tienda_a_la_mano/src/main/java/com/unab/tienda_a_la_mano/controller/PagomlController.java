package com.unab.tienda_a_la_mano.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.unab.tienda_a_la_mano.entity.PedidoEntity;
import com.unab.tienda_a_la_mano.service.IPedidoService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/pagoml")
public class PagomlController {

	// Accedemos a los metodos de la interface 
	@Autowired
	private IPedidoService service;
	
	@GetMapping("/abrirpago")
	public String crear(@RequestParam("pedido") Long codpedido) throws MPException {
		MercadoPago.SDK.setAccessToken("TEST-4977301227552893-120321-305b468b58c465aeaf71683ea9f0460f-24092619");
		//MercadoPago.SDK.setAccessToken("APP_USR-4977301227552893-120321-7fe4e17eb7326410bc0fe09eabe05f1e-24092619");
		Preference preferencia = new Preference();

		preferencia.setBackUrls(
				new BackUrls().setFailure("http://localhost:8080/failure")
				.setPending("http://localhost:8080/pending")
				.setSuccess("http://localhost:8080/success")
				);

		Optional<PedidoEntity> op = service.findById(codpedido);

		if (!op.isEmpty()) {
			PedidoEntity tabla = op.get();
			// actualizar cada propiedad
			double total = tabla.getTotal_pedido();
			float total2 = Float.parseFloat( Double.toString(total));
			
			Item item = new Item();
			item.setTitle("PAGO DEL PEDIDO "+ String.valueOf(codpedido))
			.setQuantity(1)
			.setUnitPrice(total2  );
			preferencia.appendItem(item);

			var result = preferencia.save();

			System.out.println(result.getSandboxInitPoint());

			return "redirect:"+result.getSandboxInitPoint() ;
			
		}
		else
		{
			return "No se encontro el Pedido";
		}
		

		
	}

}
