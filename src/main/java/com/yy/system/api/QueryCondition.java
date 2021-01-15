package com.yy.system.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryCondition {
	@ApiModelProperty(value="第几页，默认值为1")
	private Integer pageNum = 1;
	@ApiModelProperty(value="每页显示几条数据，默认值为0")
	private Integer pageSize = 5;
}