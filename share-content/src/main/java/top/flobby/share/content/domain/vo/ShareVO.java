package top.flobby.share.content.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.flobby.share.content.domain.entity.Share;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 分享vo
 * @create : 2023-10-12 09:09
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareVO {
    private Share share;
    private String nickname;
    private String avatarUrl;
}
