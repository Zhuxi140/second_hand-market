package com.zhuxi.common.enums;

import lombok.*;

/**
 * @author zhuxi
 */

@Getter
@AllArgsConstructor
public enum MimePart {
    suffix(0),
    prefix(1);
    private final int index;
}
