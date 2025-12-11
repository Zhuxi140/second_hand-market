package com.zhuxi.product.module.interfaces.controller;

import com.zhuxi.product.module.domain.service.ProductService;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.ShProductVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "搜索接口")
@RequestMapping("/search")
public class SearchController {

    private final ProductService productService;

    @GetMapping("/products")
    @Operation(summary = "搜索商品")
    public List<ShProductVO> search(@RequestParam(required = false) String q,
                                    @RequestParam(required = false) Integer categoryId,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "20") int size,
                                    @RequestParam(required = false) String order){
        ShProductParam p = new ShProductParam();
        p.setKeyword(q);
        p.setCategoryId(categoryId);
        p.setPage(page);
        p.setSize(size);
        p.setOrder(order);
        return productService.getShProductList(p);
    }
}
