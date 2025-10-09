package com.zhuxi.fileModule.domain.service;

import com.zhuxi.fileModule.interfaces.dto.ConfirmDTO;
import com.zhuxi.fileModule.interfaces.dto.UploadDTO;
import com.zhuxi.fileModule.interfaces.dto.ViewDTO;
import com.zhuxi.fileModule.interfaces.vo.ViewVO;

import java.net.URL;

/**
 * @author zhuxi
 */
public interface FileService {

    URL uploadFile(UploadDTO upload);

    void confirmFile(ConfirmDTO confirm);

    ViewVO getViewUrl(ViewDTO view);
}
