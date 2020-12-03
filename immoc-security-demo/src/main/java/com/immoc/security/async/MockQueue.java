package com.immoc.security.async;

import org.springframework.stereotype.Component;

@Component
public class MockQueue {
    private String placeOrder;

    private String completeOrder;

    public void setPlaceOrder(String placeOrder)  {
        new Thread(() -> {
            System.out.println("接到下单请求" + placeOrder);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;
            System.out.println("下单请求处理完成，" + placeOrder);
        }).start();
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }

    public String getPlaceOrder() {
        return placeOrder;
    }

    public String getCompleteOrder() {
        return completeOrder;
    }
}
