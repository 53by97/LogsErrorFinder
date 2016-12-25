/**
 * 
 */
package com.redbus.assignment.logserrorfinder.enums;

import static com.redbus.assignment.logserrorfinder.constants.StatusCodeConstants.*;

/**
 * It is an enum, that stores all the status codes and their corresponding
 * descriptions.
 * 
 * Currently, few of the HTTP status codes are mapped, which will differ based
 * on the actual product.
 * 
 * @author user_53by97
 *
 */
public enum StatusCodeEnums {
	/*
	 * Success and error status enums
	 */
	STATUS_200(CODE_200, DESC_200),
	STATUS_201(CODE_201, DESC_201),
	STATUS_202(CODE_202, DESC_202),
	STATUS_204(CODE_204, DESC_204),
	STATUS_400(CODE_400, DESC_400),
	STATUS_401(CODE_401, DESC_401),
	STATUS_403(CODE_403, DESC_403),
	STATUS_404(CODE_404, DESC_404),
	STATUS_405(CODE_405, DESC_405),
	STATUS_408(CODE_408, DESC_408),
	STATUS_415(CODE_415, DESC_415),
	STATUS_500(CODE_500, DESC_500),
	STATUS_502(CODE_502, DESC_502),
	STATUS_503(CODE_503, DESC_503),
	STATUS_504(CODE_504, DESC_504),
	STATUS_511(CODE_511, DESC_511);

	private int statusCode;
	private String statusDesc;

	private StatusCodeEnums(int statusCode, String statusDesc) {
		this.statusCode = statusCode;
		this.statusDesc = statusDesc;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public String getStatusDesc() {
		return this.statusDesc;
	}
	
}
