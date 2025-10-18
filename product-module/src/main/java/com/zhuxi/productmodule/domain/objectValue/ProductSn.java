package com.zhuxi.productmodule.domain.objectValue;

import lombok.Getter;
import java.util.Objects;

/**
 * @author zhuxi
 */
@Getter
public class ProductSn {
    private final String sn;

    public ProductSn(String sn) {
        if (sn == null || sn.isBlank()){
            throw new IllegalArgumentException("商品编号不能为空或为null");
        }
        this.sn = sn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sn);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj ==  this){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        ProductSn that = (ProductSn) obj;
        return Objects.equals(sn,that.sn);
    }
}
