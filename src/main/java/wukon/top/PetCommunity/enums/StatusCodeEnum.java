package wukon.top.PetCommunity.enums;

/**
 * @Auther:1306933
 * @Date:2023/3/16
 * @Description:wukon.top.PetCommunity.enums
 * @version:1.0
 * 统一状态码枚举类
 */
public enum StatusCodeEnum {
    SUCCESS(200,"成功"),
    ERROR(500,"服务器错误"),
    NOTFOUND(404,"未找到页面"),
    GOOD(1000,"我很好")
    ;

    private int code;
    private String msg;

    /**
      *功能描述：带参构造方法
      *@param: int code,String msg
      */
    private StatusCodeEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
