package com.iamcaster.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BooleanResponse {

	@ApiModelProperty
	private boolean result;

	@ApiModelProperty
	private boolean ifDuplicated;
	
}
