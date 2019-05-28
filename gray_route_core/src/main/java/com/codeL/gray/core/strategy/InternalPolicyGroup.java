package com.codeL.gray.core.strategy;

import com.codeL.gray.common.ServerTypeHolder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 17:00
 */
@Getter
@Setter
public class InternalPolicyGroup {

    /*
     * <code>
     * {
     * 	"group": {
     * 		"first": {
     * 			"divtype": "uid",
     * 			"divdata": [{
     * 				"uidset": ["111111", "2222222"],
     * 				"upstream": "10.199.137.16220886" }],
     * 			    "servertype": "dubbo"},
     * 		"second": {
     * 			"divtype": "uid",
     * 			"divdata": [{
     * 				"originName": "hhhhhh",
     * 				"uidset": ["111111", "2222222"],
     * 				"goalName": "hehehehe"
     *            }],
     * 			"servertype": "jms:P"
     *        },
     * 		"third": {
     * 			"divtype": "uid",
     * 			"divdata": [{
     * 				"originName": "hhhhhh",
     * 				"goalName": "hehehehe"
     *            }],
     * 			"servertype": "jms:C"
     *        }
     *    }
     * </code>
     */

    private Map<String, Policy> group;

}
