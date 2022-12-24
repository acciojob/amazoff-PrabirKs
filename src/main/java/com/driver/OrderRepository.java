package com.driver;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Component
public class OrderRepository {
    private HashMap<String,Order> OrderDb ;

    private HashMap<String,String> orderPartnerDB ;
    private HashMap<String,DeliveryPartner> DeliveryPartnerDb ;
    private HashMap<String, List<String>> DeliveryPartnerOrderDb ;

    public OrderRepository(){
        this.OrderDb = new HashMap<String,Order>() ;
        this.DeliveryPartnerDb = new HashMap<String,DeliveryPartner>() ;
        this.DeliveryPartnerOrderDb = new HashMap<String,List<String>>() ;
    }

    //add order
    public void StoreOrder(Order order){
        OrderDb.put(order.getId(),order) ;
    }
    //add DeliveryPartner
    public void StoreDeliveryPartner(String PartnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(PartnerId) ;
        DeliveryPartnerDb.put(PartnerId,deliveryPartner) ;
    }
    //add order-partner pair
    public void StoreOrderDeliveryPartnerPair(String orderId,String partnerId){
        List<String> OrderList = new ArrayList<>() ;
        //check whether the order and partner present in their db or not
        if(OrderDb.containsKey(orderId) && DeliveryPartnerDb.containsKey(partnerId)){
            if(!orderPartnerDB.containsKey(orderId)) {
                if (DeliveryPartnerOrderDb.containsKey(partnerId)) {
                    OrderList = DeliveryPartnerOrderDb.get(partnerId);
                }
                OrderList.add(orderId);
                DeliveryPartner partner = DeliveryPartnerDb.get(partnerId);
                partner.setNumberOfOrders(partner.getNumberOfOrders() + 1);
                DeliveryPartnerOrderDb.put(partnerId, OrderList);

                orderPartnerDB.put(orderId,partnerId) ;
            }
        }
    }

    //get order
    public Order getOrder(String orderId){
        return OrderDb.get(orderId) ;
    }
    //get delivery Partner
    public DeliveryPartner getDeliveryPartner(String partnerId){
        return DeliveryPartnerDb.get(partnerId) ;
    }

    //get orderCount of A Partner
    public int getCount(String partnerId){
        DeliveryPartner partner = DeliveryPartnerDb.get(partnerId)   ;

        int count =  partner.getNumberOfOrders() ;
//        if(DeliveryPartnerOrderDb.containsKey(partnerId))
//           count = DeliveryPartnerOrderDb.get(partnerId).size();
        return count ;
    }
    //get orders of a partner
    public List<String> getOrders(String partnerId){
        List<String> orders = new ArrayList<>() ;
        if(DeliveryPartnerOrderDb.containsKey(partnerId))
            orders = DeliveryPartnerOrderDb.get(partnerId) ;
        return orders ;
    }
    //get all orders
    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>(OrderDb.keySet()) ;
        return orders ;
    }
    //get count of unasigned orders
    public int getUnassignedOrderCount(){
        int totalOrder = OrderDb.size() ;
        int assignedOrder = 0 ;
        for(String partnerId : DeliveryPartnerOrderDb.keySet()){
            int OrderCount = DeliveryPartnerOrderDb.get(partnerId).size() ;
            assignedOrder += OrderCount ;
        }
        return totalOrder - assignedOrder ;
    }
    //get count of order left afer a time of a partner
    public int getOrderCountAfterTime(String Time,String partnerId){
        List<String> orders = DeliveryPartnerOrderDb.get(partnerId) ;


        String hh = Time.substring(0,2) ;
        String mm = Time.substring(3,Time.length()) ;
        int time = 0 ;
        time = Integer.parseInt(hh) * 60 + Integer.parseInt(mm) ;



        int orderLeftCount = 0 ;
        for(String orderId : orders){
            int deliveryTime = OrderDb.get(orderId).getDeliveryTime() ;

            if(deliveryTime > time){
                orderLeftCount++ ;
            }
        }
        return orderLeftCount ;
    }
    //get last delivery time
    public String getLastDeliveryTime(String partnerId){
        List<String> orders = DeliveryPartnerOrderDb.get(partnerId) ;
        int lastTime = 0 ;
        for(String orderId : orders){
            int deliveryTime = OrderDb.get(orderId).getDeliveryTime() ;

            if( deliveryTime > lastTime)
                lastTime = deliveryTime ;

        }
        if(lastTime == 0) return null ;
        //converting int to string
        int hh = lastTime/60 ;
        int mm = lastTime - (hh*60) ;

        String hour = "" ;
        String min = "" ;
        if(hh < 10)
            hour = "0" ;
        if(mm < 10)
            min = "0" ;

        hour += Integer.toString(hh) ;
        min += Integer.toString(mm) ;

        return hour+":"+min ;

    }
    //delete partner
    public void deletePartner(String partnerId){
        DeliveryPartnerOrderDb.remove(partnerId) ;
    }
    //delete partner
    public void deleteOrder(String orderId){
        Set<String> partners = DeliveryPartnerOrderDb.keySet() ;
        for(String partnerId : partners){
            List<String> orders = DeliveryPartnerOrderDb.get(partnerId) ;
            for(String order : orders){
                if(order.equals(orderId)){
                    orders.remove(orderId) ;
                    DeliveryPartnerOrderDb.put(partnerId,orders) ;
                    return;
                }
            }
        }
    }

}
