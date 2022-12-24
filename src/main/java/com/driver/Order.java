package com.driver;




public class Order {

    private String id;
    private int deliveryTime;

    public Order(){

    }

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id ;
        String hh = deliveryTime.substring(0,2) ;
        String mm = deliveryTime.substring(3,deliveryTime.length()) ;

        this.deliveryTime = Integer.parseInt(hh) * 60 + Integer.parseInt(mm) ;

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

}
