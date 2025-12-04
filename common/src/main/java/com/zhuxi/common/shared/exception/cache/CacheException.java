package com.zhuxi.common.shared.exception.cache;

import com.zhuxi.common.shared.exception.LocationException;

/**
 * @author zhuxi
 */
public class CacheException extends LocationException {
  private final int code;
  public CacheException(String message, int code) {
    super(message);
    this.code = code;
  }
  public CacheException(String message) {
    super(message);
    this.code = 4001;
  }
}
