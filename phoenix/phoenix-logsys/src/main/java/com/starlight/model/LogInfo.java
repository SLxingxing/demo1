package com.starlight.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.baomidou.mybatisplus.annotation.TableField;
import com.starlight.model.LogValidate.LogQueryDetail;
import com.starlight.model.LogValidate.LogQueryValidate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 日志模型
 *
 * @author wenyunfei
 */
@Data
@ApiModel(value = "LogInfo", description = "日志模型")
public class LogInfo {
    @NotNull(groups = LogQueryDetail.class)
    @Pattern(regexp = "(((\\d)|([1-9]\\d)|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d)|([1-9]\\d)|(1\\d{2})|"
        + "(2[0-4]\\d)|(25[0-5]))", message = "ip地址不正确",
        groups = {LogQueryValidate.class, LogQueryDetail.class})
    @ApiModelProperty(value = "ip地址", required = false)
    private String ip;

    @NotNull(groups = LogQueryDetail.class)
    @Pattern(regexp = "^(\\w)*", message = "服务名称不正确", groups = LogQueryDetail.class)
    @ApiModelProperty(value = "服务名称", required = false)
    private String server;

    @NotNull(groups = LogQueryDetail.class)
    @ApiModelProperty(value = "文件路径", required = false)
    private String logPath;

    @NotNull(groups = LogQueryDetail.class)
    @Pattern(regexp = "^[0-9]*", message = "接口不正确", groups = LogQueryDetail.class)
    @ApiModelProperty(value = "接口", required = false)
    private String port;

    @TableField(exist = false)
    @NotNull(groups = LogQueryDetail.class)
    @ApiModelProperty(value = "文件名称", required = false)
    private String fileName;

    @TableField(exist = false)
    @NotNull(groups = LogQueryDetail.class)
    @ApiModelProperty(value = "文件类型", required = false)
    private String fileType = "DIR";
}
