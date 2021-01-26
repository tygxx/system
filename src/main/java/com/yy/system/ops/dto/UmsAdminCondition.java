package com.yy.system.ops.dto;

import com.yy.common.dto.QueryCondition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class UmsAdminCondition extends QueryCondition {

    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
}