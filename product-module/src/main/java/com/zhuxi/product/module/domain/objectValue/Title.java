package com.zhuxi.product.module.domain.objectValue;

import lombok.Getter;

import java.util.Objects;

/**
 * @author zhuxi
 */
@Getter
public class Title {
    private final String title;

    public Title(String title) {

        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("商品标题不能为空或为null");
        }
        if(title.length() > 20){
            throw new IllegalArgumentException("商品标题长度不能超过20个字符");
        }
        this.title = title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj ==  this ){
            return true;
        }
        if (obj == null || getClass()!= obj.getClass()){
            return false;
        }
        Title that = (Title) obj;
        return Objects.equals(title, that.title);
    }
}
