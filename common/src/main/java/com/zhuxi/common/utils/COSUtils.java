package com.zhuxi.common.utils;

import com.zhuxi.common.enums.MimePart;
import com.zhuxi.common.exception.COSException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuxi
 */
@Slf4j
public class COSUtils {

    /**
     *  获取COS文件完整访问地址
     * @param url 访问地址前缀
     * @param fileName 文件相对路径
     * @return 完整访问地址
     */
    public static String getCosUrl(String url, String fileName){
        return url + "/" + fileName;
    }


    /**
     * 生成对象名(相对路径)
     * @param category 文件归属类型
     * @param suffix 文件后缀名
     * @param sn 唯一标识号
     * @return 相对路径
     */
    public static String generateFileName(String category, String suffix, String sn){
        String fileName = switch (category) {
            case "avatar" -> String.format("avatar/%s.%s", sn, suffix);
            case "product" -> String.format("product/%s.%s", sn, suffix);
            case "spec" -> String.format("spec/%s.%s", sn, suffix);
            default -> throw new COSException("暂不支持该文件类型");
        };

        log.debug("生成文件名, category:{},fileName:{}",category,fileName);
        return fileName;
    }


    /**
     * 获取文件(mime)类型/文件拓展名
     * @param mime mime类型
     * @param index 获取类型(1获取类型 2获取文件拓展名)
     * @return 类型/文件拓展名
     */
    public static String getMimeSfxPfx(String mime, MimePart index){
        if (mime == null){
            throw new IllegalArgumentException("mime不能为null");
        }
        String[] split = mime.split("/");
        if (split.length != 2){
            throw new COSException("mime格式错误");
        }
        String suffix = split[1];
        String type = split[0];
        if (index == MimePart.suffix){
            return suffix;
        }else if(index == MimePart.prefix){
            return type;
        }
        throw new IllegalArgumentException("不受支持的index");
    }
}
