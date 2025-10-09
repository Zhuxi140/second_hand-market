package com.zhuxi.fileModule.domain.repository;

import com.zhuxi.fileModule.domain.model.File;

/**
 * @author zhuxi
 */
public interface FileRepository {

    void save(File  file);


    void avatarSave(String url, String sn);

    Long getSkuIdProductId(String sn, String category);

    File getOrderSort(Long spId, String category);
}
