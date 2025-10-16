package com.zhuxi.productModule.interfaces.controller;

import com.zhuxi.productModule.domain.service.ProductService;
import com.zhuxi.productModule.interfaces.vo.CategoryTreeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhuxi
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories")
    public List<CategoryTreeVO> getCategoryList(@RequestParam(defaultValue = "100") int limit,@RequestParam(defaultValue = "0") int offset)
    {
        return productService.getCategoryList(limit, offset);
    }
}
