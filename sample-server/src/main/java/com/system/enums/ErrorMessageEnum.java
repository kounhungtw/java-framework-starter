package com.system.enums;

public enum ErrorMessageEnum {
	
	/* common */
	Update_A_New_Parent_That_References_Existing_Children("0010101", "Cannot update a new [%s] that references existing [%s]"),
	/* user */
	Incorrect_Password("0020101", "Incorrect password")
	;

	private String code;
	
    private String desc;
    
    private ErrorMessageEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
}
