package top.flobby.share.content.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 通知
 * @create : 2023-10-08 14:25
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private Long id;
    private String content;
    private Boolean showFlag;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
