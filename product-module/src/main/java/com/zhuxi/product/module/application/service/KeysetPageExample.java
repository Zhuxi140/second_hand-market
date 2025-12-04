package com.zhuxi.product.module.application.service;

import java.time.LocalDateTime;

/**
 * 为什么携带双游标：created_at + id 保证有序唯一，避免同时间戳下重复/丢失。
 */
public record Cursor(LocalDateTime createdAt, long id) { }