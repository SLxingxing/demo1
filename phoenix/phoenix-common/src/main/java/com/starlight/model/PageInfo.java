package com.starlight.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页输入参数
 *
 * @author wenyunfei
 */
@Data
@ApiModel(value = "PageInfo", description = "分页参数")
public class PageInfo<T> {
    @NotNull(message = "页数不得为空")
    @Min(value = 1,message = "页数必须大于1")
    @ApiModelProperty(value = "页数（从1开始）", required = true)
    private Integer pageIndex;

    @NotNull(message = "页码不得为空")
    @Max(value = 500,message = "页码不得超过500")
    @ApiModelProperty(value = "页码", required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "查询参数", required = false)
    @Valid
    private T data;
}
