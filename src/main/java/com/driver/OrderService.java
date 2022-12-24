package com.driver;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Component


public class OrderService {
    @Autowired
    OrderRepository orderRepository ;
    //add order
    public void addOrder(Order order){
        orderRepository.StoreOrder(order);
    }
    //add DeliveryPartner
    public void StoreDeliveryPartner(String PartnerId){
        orderRepository.StoreDeliveryPartner(PartnerId);
    }
    //add order-partner pair
    public void StoreOrderDeliveryPartnerPair(String orderId,String partnerId){
        orderRepository.StoreOrderDeliveryPartnerPair(orderId,partnerId);
    }

    //get order
    public Order getOrder(String orderId){
        return orderRepository.getOrder(orderId) ;
    }
    //get delivery Partner
    public DeliveryPartner getDeliveryPartner(String partnerId){
        return orderRepository.getDeliveryPartner(partnerId) ;
    }

    //get orderCount of A Partner
    public int getCount(String partnerId){
        return orderRepository.getCount(partnerId) ;
    }
    //get orders of a partner
    public List<String> getOrders(String partnerId){
        return orderRepository.getOrders(partnerId) ;
    }
    //get all orders
    public List<String> getAllOrders(){
        return orderRepository.getAllOrders() ;
    }
    //get count of unasigned orders
    public int getUnassignedOrderCount(){
        return orderRepository.getUnassignedOrderCount() ;
    }
    //get count of order left afer a time of a partner
    public Integer getOrderCountAfterTime(String time, String partnerId){
        return orderRepository.getOrderCountAfterTime(time,partnerId);
    }
    //get last delivery time
    public String getLastDeliveryTime(String partnerId){
        return orderRepository.getLastDeliveryTime(partnerId) ;
    }
    //delete partner
    public void deletePartner(String partnerId){
        orderRepository.deletePartner(partnerId);
    }
    //delete partner
    public void deleteOrder(String orderId){
        orderRepository.deleteOrder(orderId);
    }
}
