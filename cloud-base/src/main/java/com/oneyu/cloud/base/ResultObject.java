package com.oneyu.cloud.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Oneyu
 *
 * @CreateDate  Jun 12, 2020
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject<T> implements java.io.Serializable{
	
	/**
	 * 熔断降级CODE
	 */
	public static Integer CODE_FALLBACK = 424;  
	

	private static final long serialVersionUID = -824668426294722080L;
	private Integer code;
	private String message;
	T data;
	
	public ResultObject(Integer code, String message) {
		this(code, message, null);
	}
	

}
