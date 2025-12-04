package com.zhuxi.fileModule.application.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.zhuxi.common.shared.enums.MimePart;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.common.shared.exception.safe.COSException;
import com.zhuxi.common.shared.utils.COSUtils;
import com.zhuxi.fileModule.domain.model.File;
import com.zhuxi.fileModule.domain.repository.FileRepository;
import com.zhuxi.fileModule.domain.service.FileService;
import com.zhuxi.fileModule.interfaces.dto.ConfirmDTO;
import com.zhuxi.fileModule.interfaces.dto.UploadDTO;
import com.zhuxi.fileModule.infrastructure.config.QCloudConfig;
import com.zhuxi.fileModule.interfaces.dto.ViewDTO;
import com.zhuxi.fileModule.interfaces.dto.ViewDTOParam;
import com.zhuxi.fileModule.interfaces.vo.ViewVO;
import com.zhuxi.fileModule.interfaces.vo.ViewVOParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zhuxi
 */
@Slf4j
@Service
@RequiredArgsConstructor

/*
   文件服务实现类   待完善  权限效验  安全效验 等
 */
public class FileServiceImpl implements FileService {

    private final COSClient cosClient;
    private final QCloudConfig qCloudConfig;
    private final FileRepository fileRepository;

    private static final Set<String> SUPPORTED_IMAGE_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final Set<String> SUPPORTED_VIDEO_TYPES = Set.of(
            "video/mp4",
            "video/webm",
            "video/ogg"
    ) ;

    /**
     * 上传文件
     */
    @Override
    public URL uploadFile(UploadDTO upload){
        // 效验文件格式
        String mime = upload.getMime();
        checkMime(mime);

        String suffix = COSUtils.getMimeSfxPfx(mime, MimePart.suffix);
        String type = COSUtils.getMimeSfxPfx(mime, MimePart.prefix);

        // 效验文件大小
        checkSize(upload.getSize(),type);

        // 生成文件对象
        String fileName = COSUtils.generateFileName(upload.getCategory(), suffix, upload.getSn());

        // 生成预签名URL
        return generateUrl(fileName,mime);
    }

    /**
     * 确认文件上传状态
     */
    @Override
    @Transactional(rollbackFor = {COSException.class, BusinessException.class})
    public void confirmFile(ConfirmDTO confirm) {
        String sn = confirm.getSn();
        String category = confirm.getCategory();
        String mime = confirm.getMime();

        if ("avatar".equals(category)) {
            String cosUrl = getCosUrl(mime, sn,category);
            fileRepository.avatarSave(cosUrl, sn);
        }else if("icon".equals(category)){
            String cosUrl = getCosUrl(mime, sn,category);
            fileRepository.iconSave(cosUrl, (Integer.parseInt(sn)));
        }else {

            Long spId = fileRepository.getSkuIdProductId(sn, category);
            File file = fileRepository.getOrderSort(spId, category);

            // 确认文件
            file.confirmFile(confirm, spId);

            // 写入
            fileRepository.save(file);
        }
    }

    /**
     * 获取文件查看签名
     */
    @Override
    public ViewVO getViewUrl(ViewDTO view) {

        List<ViewDTOParam> views = view.getViews();
        List<ViewVOParam> viewParam = new ArrayList<>();
        for (ViewDTOParam param : views) {
            try {
                String fileKey = param.getFileKey();
                /*String category = param.getCategory();*/
                GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(qCloudConfig.getBucketName(), fileKey, HttpMethodName.GET);

                // getViewExpireTime返回时间单位为分
                long expireTime = System.currentTimeMillis() + (qCloudConfig.getViewExpireTime() * 60L * 1000L);
                request.setExpiration(new Date(expireTime));
                URL url = cosClient.generatePresignedUrl(request);
                ViewVOParam vp = new ViewVOParam(fileKey, url, expireTime);
                viewParam.add(vp);
            }catch (Exception e){
                log.error("获取文件查看签名失败 fileName: {}", param.getFileKey());
                ViewVOParam vp = new ViewVOParam(param.getFileKey(), null, 0L);
                viewParam.add(vp);
            }
        }

        return new ViewVO(viewParam);
    }


    // 效验文件大小
    private void checkSize(Long size,String type){
        switch (type){
            case "image"->{
                if (size > qCloudConfig.getImageSize() * 1024L * 1024L){
                    throw new COSException("图片文件大小不能超过10M");
                }
            }
            case "video"->{
                if (size > qCloudConfig.getVideoSize() * 1024L * 1024L){
                    throw new  COSException("视频文件大小不能超过300M");
                }
            }
            default -> throw new COSException("暂不支持该文件类型");
        }
    }

    // 效验文件格式
    private void checkMime(String mime){
        boolean isSupport = SUPPORTED_IMAGE_TYPES.contains(mime) || SUPPORTED_VIDEO_TYPES.contains(mime);
        if (!isSupport){
            throw new COSException("暂不支持该文件类型");
        }
    }

    // 生成预签名URL
    private URL generateUrl(String fileName,String mime){
        try {
            int expireTime = qCloudConfig.getExpireTime();
            Date expirationDate = new Date(System.currentTimeMillis() + (expireTime * 1000L));

            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(qCloudConfig.getBucketName(), fileName, HttpMethodName.PUT);
            request.setContentType(mime);
            request.setExpiration(expirationDate);

            log.debug("生成预签名URl, bucket:{},fileName:{},expireTime:{}秒",
                    qCloudConfig.getBucketName(),
                    fileName,
                    expireTime
                    );

            return cosClient.generatePresignedUrl(request);
        }catch (Exception e){
            log.error("生成URL失败 fileName: {}", fileName);
            throw new COSException("生成URL失败,请稍后重试");
        }
    }

    private String getCosUrl(String mime, String sn,String category) {
        String suffix = COSUtils.getMimeSfxPfx(mime, MimePart.suffix);
        return  COSUtils.generateFileName(category, suffix, sn);
    }

}
