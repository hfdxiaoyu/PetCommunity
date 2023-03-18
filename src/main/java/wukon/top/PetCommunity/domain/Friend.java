package wukon.top.PetCommunity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 好友实体类
 * @TableName friend
 */
@TableName(value ="friend")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关注id
     */
    private Long attentionId;

    /**
     * 关注状态（0表示未关注，1表示已关注）
     */
    private String attentionStatus;

    /**
     * 关注时间
     */
    private Date attentionTime;

    /**
     * 被关注用户id
     */
    private Long byAttentionId;

    /**
     * 被关注状态
     */
    private String byAttentionStatus;

    /**
     * 被关注时间
     */
    private Date byAttentionTime;

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
        Friend other = (Friend) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAttentionId() == null ? other.getAttentionId() == null : this.getAttentionId().equals(other.getAttentionId()))
            && (this.getAttentionStatus() == null ? other.getAttentionStatus() == null : this.getAttentionStatus().equals(other.getAttentionStatus()))
            && (this.getAttentionTime() == null ? other.getAttentionTime() == null : this.getAttentionTime().equals(other.getAttentionTime()))
            && (this.getByAttentionId() == null ? other.getByAttentionId() == null : this.getByAttentionId().equals(other.getByAttentionId()))
            && (this.getByAttentionStatus() == null ? other.getByAttentionStatus() == null : this.getByAttentionStatus().equals(other.getByAttentionStatus()))
            && (this.getByAttentionTime() == null ? other.getByAttentionTime() == null : this.getByAttentionTime().equals(other.getByAttentionTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAttentionId() == null) ? 0 : getAttentionId().hashCode());
        result = prime * result + ((getAttentionStatus() == null) ? 0 : getAttentionStatus().hashCode());
        result = prime * result + ((getAttentionTime() == null) ? 0 : getAttentionTime().hashCode());
        result = prime * result + ((getByAttentionId() == null) ? 0 : getByAttentionId().hashCode());
        result = prime * result + ((getByAttentionStatus() == null) ? 0 : getByAttentionStatus().hashCode());
        result = prime * result + ((getByAttentionTime() == null) ? 0 : getByAttentionTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", attentionId=").append(attentionId);
        sb.append(", attentionStatus=").append(attentionStatus);
        sb.append(", attentionTime=").append(attentionTime);
        sb.append(", byAttentionId=").append(byAttentionId);
        sb.append(", byAttentionStatus=").append(byAttentionStatus);
        sb.append(", byAttentionTime=").append(byAttentionTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}