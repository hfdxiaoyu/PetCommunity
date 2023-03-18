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
 * 聊天表
 * @TableName chatting_records
 */
@TableName(value ="chatting_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChattingRecords implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发送用户id
     */
    private Long sendUserId;

    /**
     * 接收用户
     */
    private Long acceptUserId;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendtime;

    /**
     * 发送通道名字
     */
    private String channelName;

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
        ChattingRecords other = (ChattingRecords) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSendUserId() == null ? other.getSendUserId() == null : this.getSendUserId().equals(other.getSendUserId()))
            && (this.getAcceptUserId() == null ? other.getAcceptUserId() == null : this.getAcceptUserId().equals(other.getAcceptUserId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getSendtime() == null ? other.getSendtime() == null : this.getSendtime().equals(other.getSendtime()))
            && (this.getChannelName() == null ? other.getChannelName() == null : this.getChannelName().equals(other.getChannelName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSendUserId() == null) ? 0 : getSendUserId().hashCode());
        result = prime * result + ((getAcceptUserId() == null) ? 0 : getAcceptUserId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getSendtime() == null) ? 0 : getSendtime().hashCode());
        result = prime * result + ((getChannelName() == null) ? 0 : getChannelName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sendUserId=").append(sendUserId);
        sb.append(", acceptUserId=").append(acceptUserId);
        sb.append(", content=").append(content);
        sb.append(", sendtime=").append(sendtime);
        sb.append(", channelName=").append(channelName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}