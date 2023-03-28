package wukon.top.PetCommunity.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther:1306933
 * @Date:2023/3/28
 * @Description:wukon.top.PetCommunity.domain.po
 * @version:1.0
 * 首页需要显示的数据封装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexContent{
    private Long userId; //用户id
    private String userName; //用户名
    private String nickName; //昵称
    private String avatar; //头像url
    private String contentId;//内容id
    private String title; //标题
    private String content; //内容
    private String img; //图片
    private Long viewCount; //访问量
    private Date createtime; //创建时间

}
