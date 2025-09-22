package com.zhuxi.pojo.DTO.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhuxi.common.constant.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSdDTO {

    @JsonIgnore
    private String productSn;
    private Long sellerId;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Size(min = 8,max = 64,message = "标题长度在8-64之间")
    private String title;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String description;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private Long categoryId;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private BigDecimal price;
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private Integer conditionId;
    @JsonIgnore
    private Integer status = 1;
    private String location;
}
