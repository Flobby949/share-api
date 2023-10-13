package top.flobby.share.content.domain.dto;

import lombok.Data;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 投稿dto
 * @create : 2023-10-13 13:47
 **/

@Data
public class ShareSubmitDTO {
    private Long userId;
    private String author;
    /**
     * 标题
     */
    private String title;
    /**
     * 是否原创 true:是
     */
    private Boolean isOriginal;
    /**
     * 封面
     */
    private String cover;
    /**
     * 概要信息
     */
    private String summary;
    /**
     * 价格（需要的积分）
     */
    private Integer price;
    /**
     * 下载地址
     */
    private String downloadUrl;
}
