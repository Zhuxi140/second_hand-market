package com.zhuxi.productmodule.domain.objectValue;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author zhuxi
 */
@Getter
public class HostScore {
    private final BigDecimal score;

    public HostScore(BigDecimal score) {
        if (score == null || score.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("评分不能小于0或为null");
        }
        if (score.compareTo(new BigDecimal(100)) > 0){
            throw new IllegalArgumentException("评分不能大于100");
        }
        this.score = score;
    }

    @Override
    public int hashCode() {
       return Objects.hash( score);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj ==  this ){
            return true;
        }

        if (obj == null || getClass()!= obj.getClass()){
            return false;
        }

        HostScore that = (HostScore) obj;
        return this.score.compareTo(that.score) == 0;
    }
}
