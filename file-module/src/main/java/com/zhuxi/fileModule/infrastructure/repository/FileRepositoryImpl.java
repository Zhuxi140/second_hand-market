package com.zhuxi.fileModule.infrastructure.repository;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.constant.CommonMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.fileModule.domain.model.File;
import com.zhuxi.fileModule.domain.repository.FileRepository;
import com.zhuxi.fileModule.infrastructure.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author zhuxi
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class FileRepositoryImpl implements FileRepository {

    private final FileMapper fileMapper;


    @Override
    public void save(File file) {
        if (file.getId() == null){
            int save = fileMapper.save(file);
            if (save < 1){
                log.error("File-save()-: cases: {}", CommonMessage.DATABASE_INSERT_EXCEPTION);
                throw new BusinessException(BusinessMessage.FILE_DATA_SAVE_ERROR);
            }
        }else{
            int update = fileMapper.update(file);
            if (update < 1){
                log.error("File-update()-: cases: {}", CommonMessage.DATABASE_UPDATE_EXCEPTION);
                throw new BusinessException(BusinessMessage.FILE_DATA_UPDATE_ERROR);
            }
        }
    }


    @Override
    public void avatarSave(String url, String sn) {

        int i = fileMapper.updateAvatar(url, sn);
        if (i < 1){
            log.error("File-avatarSave()-: cases: {}", CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new BusinessException(BusinessMessage.FILE_DATA_SAVE_ERROR);
        }
    }

    @Override
    public void iconSave(String url, int id) {
        int i = fileMapper.updateIcon(url, id);
        if (i < 1){
            log.error("File-iconSave()-: case: {}",CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new BusinessException(BusinessMessage.FILE_DATA_UPDATE_ERROR);
        }
    }

    @Override
    public Long getSkuIdProductId(String sn, String category) {
        Long id = switch (category){
            case "product" -> fileMapper.findProductIdBySn(sn);
            case "spec" -> fileMapper.findSkuIdBySn(sn);
            default -> null;
        };

        if (id == null){
            log.error("File-getSkuIdProductId()-: cases: {}", CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return id;
    }

    @Override
    public File getOrderSort(Long spId, String category) {
        File file = switch (category){
            case "product" -> fileMapper.findOrderSortBypId(spId);
            case "spec" -> fileMapper.findOrderSortBySkuId(spId);
            default -> null;
        };

        if (file ==  null){
            log.error("File-getOrderSort()-: cases: {}", CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return file;
    }
}
