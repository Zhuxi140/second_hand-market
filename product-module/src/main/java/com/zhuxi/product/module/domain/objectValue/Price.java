package com.zhuxi.product.module.domain.objectValue;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author zhuxi
 */
@Getter
public class Price {
    private final BigDecimal price;

    @JsonCreator
    public Price(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("价格不能小于0或为null");
        }
        this.price = price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj ==  this ){
            return true;
        }

        if (obj == null || getClass()!= obj.getClass()){
            return false;
        }

        Price that = (Price) obj;
        return this.price.compareTo(that.price) == 0;
    }
}
