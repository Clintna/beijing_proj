package beijing.transport.beijing_proj.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Jinglin
 * @date 2022年4月29日15:48:30
 */
public enum ErrorCodeEnum {

    SUCCEED_CODE("00000", "处理成功"),
    FAILED_CODE("00001", "系统异常"),
    FAILED_LOGIN("00002", "登陆失败，请重新再试,"),
    NO_ACCOUNT_CODE("00003", "账户不存在"),
    MISSING_PARAM_CODE("10000", "参数为空"),
    ILLEGAL_PARAM_CODE("10001", "参数非法"),
    NOT_SUPPORTED_FILE_CODE("10002", "不支持的文件格式"),
    NO_AUTHORITY_CODE("11000", "无权限操作"),
    BIZ_FAILED_CODE("21000", "业务处理失败"),
    BIZ_PROCESSING_CODE("21001", "业务处理中"),
    SERVICE_ERROR("20000", "处理失败"),
    MISS_TARGET_CODE("20003", "需操作的对象不存在"),
    REPEAT_TARGET_CODE("20001", "需操作的对象已经存在"),
    ILLEGAL_OPERATE_CODE("20002", "非法的业务操作"),
    REQUEST_TIME_OUT("30001", "调用第三方接口超时"),

    ;

    private String code;
    private String msg;


    private ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static ErrorCodeEnum queryByCode(final String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        } else {
            ErrorCodeEnum[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                ErrorCodeEnum errCodeEnum = var1[var3];
                if (errCodeEnum.code.equals(code)) {
                    return errCodeEnum;
                }
            }

            return null;
        }
    }
}