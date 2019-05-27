package com.codeL.gray.core.strategy;

import lombok.Getter;
import lombok.Setter;


/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Getter
@Setter
public class Policy {

    /*
     * <code>
     *      {
     *      "divtype": "uid",
     * 		"divdata": [
     * 	   	    {
     * 			    "uidset": ["111111", "2222222"],
     * 		        "upstream": "10.199.137.16220886"
     * 		    }],
     * 		"servertype": "dubbo",
     * 		}
     * </code>
     */

    /**
     * 分流类型
     */
    private String divtype;
    /**
     * 分流数据
     */
    private String divdata;
    /**
     * 服务类型
     */
    private String servertype;
}
