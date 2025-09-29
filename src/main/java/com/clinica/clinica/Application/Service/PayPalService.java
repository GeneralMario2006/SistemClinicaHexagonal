/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinica.clinica.Application.Service;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author mr587
 */

@Service
public class PayPalService {
    private PayPalHttpClient client;

    public PayPalService(PayPalHttpClient client) {
        this.client = client;
    }
    
    public Map<String, String>CreateOrder(String tipoConsulta) throws IOException{
           String especialidad = tipoConsulta;
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
       response.put("orderId", order.id()); 
       return response;
    }
}
