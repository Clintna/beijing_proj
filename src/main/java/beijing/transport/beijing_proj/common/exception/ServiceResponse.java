package beijing.transport.beijing_proj.common.exception;

import beijing.transport.beijing_proj.common.enums.ErrorCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一接口返回值
 *
 * @author Jinglin
 */
@Data
@ApiModel("统一接口返回对象")
public class ServiceResponse<T> implements Serializable {

    @ApiModelProperty("数据")
    private T data;

    @ApiModelProperty("状态编号")
    private String code;

    @ApiModelProperty("消息内容")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)//如果为null则不进行序列化
    @ApiModelProperty("报错信息")
    private String error;

    public ServiceResponse(RuntimeException e) {
        this.setCode(ErrorCodeEnum.FAILED_CODE.getCode());
        this.setMessage(ErrorCodeEnum.FAILED_CODE.getMsg());
//        StringBuilder buffer = new StringBuilder(e.getMessage()).append(",堆栈信息：");
//        for (StackTraceElement element : e.getStackTrace()) {
//            String className = element.getClassName();
//            if (className.contains("com.bhhyy.aircraft")) {
//                buffer.append(className).append("（")
//                        .append(element.getMethodName()).append("：")
//                        .append(element.getLineNumber()).append("）。")
//                        .append(System.lineSeparator());
//            }
//        }
        this.setError(e.getMessage());
    }
}
