package com.linh.OrderService.serice;

import com.linh.OrderService.model.KeyCloakCurrentUser;
import com.linh.OrderService.model.OrderRequest;
import com.linh.OrderService.model.OrderResponse;

public interface OrderService {

    long placeOrder(OrderRequest request);

    OrderResponse getOrderDetails(long orderId);

}
