package com.iamcaster.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResultResponse {
	
	@ApiModelProperty(value = "result", example = "success")
	private String result;
}
