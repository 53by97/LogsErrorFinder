package com.redbus.assignment.logserrorfinder.constants;

/**
 * It is a constant class, that holds all the status codes and their
 * descriptions.
 * 
 * Currently, few of the HTTP status codes are mapped, which will differ based
 * on the actual product.
 * 
 * @author user_53by97
 *
 */
public class StatusCodeConstants {

	private StatusCodeConstants() {
	}

	/*
	 * Success status codes
	 */
	public static final int CODE_200 = 200;
	public static final String DESC_200 = "Ok";
	public static final int CODE_201 = 201;
	public static final String DESC_201 = "Created";
	public static final int CODE_202 = 202;
	public static final String DESC_202 = "Accepted";
	public static final int CODE_204 = 204;
	public static final String DESC_204 = "No Content";

	/*
	 * Client error status codes
	 */
	public static final int CODE_400 = 400;
	public static final String DESC_400 = "Bad Request";
	public static final int CODE_401 = 401;
	public static final String DESC_401 = "Unauthorized";
	public static final int CODE_403 = 403;
	public static final String DESC_403 = "Forbidden";
	public static final int CODE_404 = 404;
	public static final String DESC_404 = "Not Found";
	public static final int CODE_405 = 405;
	public static final String DESC_405 = "Method Not Allowed";
	public static final int CODE_408 = 408;
	public static final String DESC_408 = "Request Timeout";
	public static final int CODE_415 = 415;
	public static final String DESC_415 = "Unsupported Media Type";

	/*
	 * Server error status codes
	 */
	public static final int CODE_500 = 500;
	public static final String DESC_500 = "Internal Server Error";
	public static final int CODE_502 = 502;
	public static final String DESC_502 = "Bad Gateway";
	public static final int CODE_503 = 503;
	public static final String DESC_503 = "Service Unavailable";
	public static final int CODE_504 = 504;
	public static final String DESC_504 = "Gateway Timeout";
	public static final int CODE_511 = 511;
	public static final String DESC_511 = "Network Authentication Required";
	
}
