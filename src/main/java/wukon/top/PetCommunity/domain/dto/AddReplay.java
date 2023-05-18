package wukon.top.PetCommunity.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  *功能描述：添加评论实体类
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReplay {
    private Long id;
    //评论类型（0代表文章评论，1代表友链评论）
    private String type;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;

    private Integer delFlag;

}