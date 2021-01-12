package com.hermanovich.accountingsystem.controller.order;

import com.hermanovich.accountingsystem.controller.security.util.AuthenticationHelper;
import com.hermanovich.accountingsystem.controller.util.OrderConverter;
import com.hermanovich.accountingsystem.dto.order.OrderDto;
import com.hermanovich.accountingsystem.dto.order.OrderDtoStatus;
import com.hermanovich.accountingsystem.dto.user.UserDetailDto;
import com.hermanovich.accountingsystem.service.order.OrderService;
import com.hermanovich.accountingsystem.service.sortertype.OrderSorterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> addOrder(@RequestBody OrderDto orderDto,
                                         @RequestParam(name = "days") Integer numberOfDays) {
        orderDto.setUser(UserDetailDto.builder().login(AuthenticationHelper.getAuthenticationUserName()).build());
        orderService.addOrder(OrderConverter.convertOrderDtoToOrder(orderDto), numberOfDays);
        log.info("Order added");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.removeOrder(id);
        log.info("Order deleted");
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/completion")
    public ResponseEntity<Void> closeOrder() {
        orderService.closeOrder(AuthenticationHelper.getAuthenticationUserName());
        log.info("Order closed");
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateOrder(@RequestBody OrderDto orderDto) {
        orderService.updateOrder(OrderConverter.convertOrderDtoToOrder(orderDto));
        log.info("Order updated");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto getOrder(@PathVariable Integer id) {
        return OrderConverter.convertOrderToOrderDto(orderService.getOrder(id));
    }

    @GetMapping("/histories")
    public List<OrderDto> getUserOrders() {
        return OrderConverter.convertOrderListToOrderDtoList(
                orderService.getOrderList(AuthenticationHelper.getAuthenticationUserName()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDto> getOrderList(@RequestParam(name = "type", required = false) OrderSorterType sorterType,
            @RequestParam(name = "status", required = false) OrderDtoStatus orderStatus) {
        if (orderStatus == OrderDtoStatus.EXPIRED) {
            return getExpiredOrderList(sorterType);
        }
        if (orderStatus == OrderDtoStatus.NOT_EXPIRED) {
            return getNotExpiredOrdersList(sorterType);
        }
        if (sorterType == null) {
            return OrderConverter.convertOrderListToOrderDtoList(orderService.getOrderList());
        }
        return OrderConverter.convertOrderListToOrderDtoList(orderService.getSortedOrderList(sorterType));
    }

    @GetMapping("/amountMoney")
    @PreAuthorize("hasRole('ADMIN')")
    public BigDecimal getAmountMoney(@RequestParam(name = "days") Integer numberOfDays) {
        return orderService.getAmountMoney(numberOfDays);
    }

    private List<OrderDto> getNotExpiredOrdersList(OrderSorterType sorterType) {
        if (sorterType == null) {
            return OrderConverter.convertOrderListToOrderDtoList(orderService.getNotExpiredOrderList());
        }
        return OrderConverter.convertOrderListToOrderDtoList(orderService.getSortedNotExpiredOrderList(sorterType));
    }

    private List<OrderDto> getExpiredOrderList(OrderSorterType sorterType) {
        if (sorterType == null) {
            return OrderConverter.convertOrderListToOrderDtoList(orderService.getExpiredOrderList());
        }
        return OrderConverter.convertOrderListToOrderDtoList(orderService.getSortedExpiredOrderList(sorterType));
    }
}
