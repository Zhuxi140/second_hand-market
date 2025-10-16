package com.zhuxi.productModule.domain.objectValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author zhuxi
 */
@Getter
public class Location {
    private final String location;

    public Location(String location) {
        if (location == null || location.isBlank()){
            throw new IllegalArgumentException("objectValue-location is null or empty");
        }
        if (location.length() > 255){
            throw new IllegalArgumentException("objectValue-location is too long");
        }
        this.location = location;
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

        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
