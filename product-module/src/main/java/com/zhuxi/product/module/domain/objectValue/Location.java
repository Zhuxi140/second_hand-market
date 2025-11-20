package com.zhuxi.product.module.domain.objectValue;

import lombok.Getter;

import java.util.Objects;

/**
 * @author zhuxi
 */
@Getter
public class Location {
    private final String locationCode;

    public Location(String location) {
        if (location == null || location.isBlank()){
            throw new IllegalArgumentException("objectValue-location is null or empty");
        }
        if (location.length() > 255){
            throw new IllegalArgumentException("objectValue-location is too long");
        }
        this.locationCode = location;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Location that = (Location) obj;

        return Objects.equals(locationCode, that.locationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationCode);
    }
}
