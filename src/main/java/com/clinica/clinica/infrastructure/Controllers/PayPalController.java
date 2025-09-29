/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.infrastructure.Controllers;

import com.clinica.clinica.Application.Service.ServiceCita;
import com.clinica.clinica.domain.CitasDomain;
import com.clinica.clinica.infrastructure.Mappers.MapperCitas;
import com.clinica.clinica.infrastructure.Paypal.PayPalConfig;
import com.clinica.clinica.infrastructure.RequestDTO.CitaDTO;
import com.clinica.clinica.infrastructure.RequestDTO.PayRequest;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/Paypal")
public class PayPalController {
    
    private PayPalHttpClient client;
    
    @Autowired
    private ServiceCita serviceCita;
    
    @Autowired
    MapperCitas mapperCitas;

    
    public PayPalController(PayPalHttpClient client) {
        this.client = client;
    }
    
    @PostMapping("/Create")
    @ResponseBody
    public Map<String, String>CreateOrder(@RequestBody PayRequest pay) throws IOException {
        String especialidad = pay.getEspecialidad();
        String precio="";
        if (especialidad.equals("CIRUJANO")) {
            precio="500";
        }
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        
        ApplicationContext context = new ApplicationContext()
                .returnUrl("http://localhost:8080/Paypal/success")
                .cancelUrl("http://localhost:8080/Paypal/cancel");
        
        List<PurchaseUnitRequest> units =new ArrayList<>();
        
        units.add(new PurchaseUnitRequest()
        .amountWithBreakdown(new AmountWithBreakdown()
        .currencyCode("USD")
                .value(precio)));
        
       orderRequest.purchaseUnits(units);
       orderRequest.applicationContext(context);
       
       OrdersCreateRequest request = new OrdersCreateRequest()
               .requestBody(orderRequest);
        
       Order order = client.execute(request).result();
       
       String link = order.links().stream()
               .filter(linkk -> "approve".equals(linkk.rel()))
               .findFirst()
               .map(LinkDescription::href)
               .orElseThrow(() -> new RuntimeException("No se encontró"));
       
       Map<String, String> response = new HashMap<>();
       response.put("approvalUrl", link);
       return response;
    }
    
    @GetMapping("/success")
    public RedirectView Success(@RequestParam String token) throws IOException {
        OrdersCaptureRequest request= new OrdersCaptureRequest(token);
        Order order= client.execute(request).result();
        String orderId= order.id();
        RedirectView redirect = new RedirectView();
        
        serviceCita.FindByOrderId(orderId);
        
        redirect.setUrl("http://localhost:5500/success.html");
        return redirect;
    }
}
