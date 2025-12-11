package com.zhuxi.order.module.interfaces.controller;

import com.zhuxi.common.interfaces.result.Result;
import com.zhuxi.common.shared.annotation.PermissionCheck;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.order.module.domain.service.OrderService;
import com.zhuxi.order.module.interfaces.dto.CancelOrderDTO;
import com.zhuxi.order.module.interfaces.dto.CreateOrderDTO;
import com.zhuxi.order.module.interfaces.dto.UpdateOrderStatusDTO;
import com.zhuxi.order.module.interfaces.vo.OrderDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Tag(name = "订单接口", description = "订单相关接口")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "创建订单")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "创建成功"),
            @ApiResponse(responseCode = "500", description = "创建失败")
    })
    @PermissionCheck(Role = Role.user, permission = "user:createOrder", enableDataOwnership = true,logic = PermissionCheck.Logic.OR)
    public Result<String> create(@RequestBody @Valid CreateOrderDTO dto, @RequestParam String userSn){
        String orderSn = orderService.create(dto,userSn);
        return Result.success(orderSn);
    }

    @GetMapping("/{orderSn}")
    @Operation(summary = "订单详情")
    @PermissionCheck(Role = Role.user, permission = "user:getOrderDetail", enableDataOwnership = false)
    public Result<OrderDetailVO> detail(@PathVariable String orderSn){
        OrderDetailVO vo = orderService.detail(orderSn);
        return Result.success(vo);
    }

    @PutMapping("/{orderSn}/status")
    @Operation(summary = "更新订单状态")
    @PermissionCheck(Role = Role.user, permission = "user:updateOrderStatus", enableDataOwnership = false)
    public Result<String> updateStatus(@PathVariable String orderSn, @RequestBody UpdateOrderStatusDTO dto){
        orderService.updateStatus(orderSn, dto);
        return Result.success();
    }

    @GetMapping("/user/{userSn}")
    @Operation(summary = "用户订单列表")
    @PermissionCheck(Role = Role.user, permission = "user:getOrderList", enableDataOwnership = true)
    public Result<List<OrderDetailVO>> list(@PathVariable String userSn, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size){
        List<OrderDetailVO> list = orderService.list(userSn, page, size);
        return Result.success(list);
    }

    @PostMapping("/{orderSn}/cancel")
    @Operation(summary = "取消订单")
    @PermissionCheck(Role = Role.user, permission = "user:cancelOrder", enableDataOwnership = false)
    public Result<String> cancel(@PathVariable String orderSn, @RequestBody CancelOrderDTO dto){
        orderService.cancel(orderSn, dto);
        return Result.success();
    }
}
