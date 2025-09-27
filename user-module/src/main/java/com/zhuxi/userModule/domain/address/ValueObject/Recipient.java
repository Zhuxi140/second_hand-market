package com.zhuxi.userModule.domain.address.ValueObject;

import com.zhuxi.userModule.domain.user.valueObject.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipient {
    private String consignee;
    private String sex;
    private Phone phone;

    public Recipient setConsignee(String consignee) {
        this.consignee = consignee;
        return this;
    }

    public Recipient setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public Recipient setPhone(Phone phone) {
        this.phone = phone;
        return this;
    }
}
