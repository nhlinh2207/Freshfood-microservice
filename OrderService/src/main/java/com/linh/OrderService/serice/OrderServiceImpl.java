package com.linh.OrderService.serice;

import com.linh.OrderService.entity.Order;
import com.linh.OrderService.exception.CustomException;
import com.linh.OrderService.external.client.PaymentService;
import com.linh.OrderService.external.client.ProductService;
import com.linh.OrderService.external.request.PaymentRequest;
import com.linh.OrderService.external.response.PaymentResponse;
import com.linh.OrderService.model.OrderRequest;
import com.linh.OrderService.model.OrderResponse;
import com.linh.OrderService.model.ProductResponse;
import com.linh.OrderService.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    @Override
    public long placeOrder(OrderRequest request) {

        log.info("Placing order request {}", request);

        // call Product Service to reduce quantity
        productService.reduceQuantity(request.getProductId(), request.getQuantity());

        Order order = Order.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .amount(request.getTotalAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .build();

        order = orderRepository.saveAndFlush(order);

        log.info("Calling PaymentService to complete Payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                        .orderId(order.getId())
                        .paymentMode(request.getPaymentMode())
                        .amount(request.getTotalAmount())
                                .build();

        String orderStatus = null;
        try{
            ResponseEntity<Long> paymentResult = paymentService.doPayment(paymentRequest);
            log.info("Payment done Successfully !. Payment Id: {}", paymentResult.getBody());
            orderStatus = "PLACED";
        }catch (Exception e){
            log.info("Error occured in PaymentService");
            orderStatus = "FAILED";
        }

        order.setOrderStatus(orderStatus);
        order = orderRepository.saveAndFlush(order);

        log.info("Order placed successfully with id : {}", order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get oder details by id: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(
                        "Order not found by Id "+orderId,
                        "NOT_FOUND",
                        404
                ));

        log.info("Invoking ProductService to get Product Detail");
//        ProductResponse productResponse = restTemplate
//                .getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(), ProductResponse.class);

        ProductResponse productResponse = webClient
                .get()
                .uri("http://PRODUCT-SERVICE/product/"+order.getProductId())
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productId(productResponse.getProductId())
                .quantity(productResponse.getQuantity())
                .price(productResponse.getPrice())
                .productName(productResponse.getProductName())
                .build();

        log.info("Invoking PaymentService to get Payment Detail");
//        PaymentResponse paymentResponse = restTemplate
//                .getForObject("http://PAYMENT-SERVICE/payment/order/"+order.getId(), PaymentResponse.class);

        PaymentResponse paymentResponse = webClient
                .get()
                .uri("http://PAYMENT-SERVICE/payment/order/"+order.getId())
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block();

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentStatus(paymentResponse.getStatus())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        OrderResponse response = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        return response;
    }
}
