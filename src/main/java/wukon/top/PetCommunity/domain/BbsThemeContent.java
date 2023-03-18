package wukon.top.PetCommunity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 内容表
 * @TableName bbs_theme_content
 */
@TableName(value ="bbs_theme_content")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BbsThemeContent implements Serializable {
    /**
     * 主题id
     */
    @TableId
    private Long themeId;

    /**
     * 内容id
     */
    @TableField
    private Long contentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BbsThemeContent other = (BbsThemeContent) that;
        return (this.getThemeId() == null ? other.getThemeId() == null : this.getThemeId().equals(other.getThemeId()))
            && (this.getContentId() == null ? other.getContentId() == null : this.getContentId().equals(other.getContentId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getThemeId() == null) ? 0 : getThemeId().hashCode());
        result = prime * result + ((getContentId() == null) ? 0 : getContentId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", themeId=").append(themeId);
        sb.append(", contentId=").append(contentId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}