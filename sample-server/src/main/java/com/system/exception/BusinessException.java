package com.system.exception;

import com.system.enums.ErrorMessageEnum;

public class BusinessException extends Exception {

	private static final long serialVersionUID = -1690847395800834511L;
	
	private String errorCode;
	
	private Object[] param;

    public BusinessException() {
        super();
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(ErrorMessageEnum errorMessageEnum, Object... param) {
		super(String.format("[%s] ", errorMessageEnum.getCode()) + String.format(errorMessageEnum.getDesc(), param));
        this.errorCode = errorMessageEnum.getCode();
        this.param = param;
    }

    public String getErrorCode() {
        return errorCode;
    }
    
    public Object[] getParam() {
        return param;
    }

}
