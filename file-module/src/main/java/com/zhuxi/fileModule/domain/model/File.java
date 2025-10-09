package com.zhuxi.fileModule.domain.model;

import com.zhuxi.common.enums.MimePart;
import com.zhuxi.common.utils.COSUtils;
import com.zhuxi.fileModule.interfaces.dto.ConfirmDTO;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author zhuxi
 */
@Getter
public class File {

    private Long id;
    private Long productId;
    private Long skuId;
    private String imageUrl;
    private int imageType;        /*封面  详细图   规格图*/
    private int sortOrder;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private File(Long id, Long productId, Long skuId, String imageUrl, int imageType, int sortOrder, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.productId = productId;
        this.skuId = skuId;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.sortOrder = sortOrder;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }


    public void confirmFile(ConfirmDTO  confirm,Long spId){
        String suffix = COSUtils.getMimeSfxPfx(confirm.getMime(), MimePart.suffix);
        this.imageUrl = COSUtils.generateFileName(confirm.getCategory(), suffix, confirm.getSn());
        this.imageType = confirm.getFileType();
        switch (confirm.getCategory()){
            case "product":{
                this.productId = spId;
            } break;
            case "spec":{
                this.skuId = spId;
            } break;
        }
        this.sortOrder = this.sortOrder + 10;
    }

}
