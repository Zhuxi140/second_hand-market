package com.zhuxi.product.module.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuxi
 */
@Getter
public enum ImageType {

    Cover(1,"封面图"),
    Detail(2,"详细图"),
    skuImage(3,"规格图");

    private final Integer code;
    private final String message;

    @JsonCreator
    ImageType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ImageType getByCode(Integer code) {
        for (ImageType value : ImageType.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("不受支持的code");
    }
}
