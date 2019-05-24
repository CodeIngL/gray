package com.codeL.gray.common.http;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class Response {


    public static final  String CODE_SUCCESS = "00000000";

    /**
     * head : {"code":"00000000","description":"�ɹ�","msg":"�ɹ�","time":"2019-04-10 15:19:34","status":"Y"}
     * body : null
     */

    private HeadBean head;
    private Object body;

    public HeadBean getHead() {
        return head;
    }

    public void setHead(HeadBean head) {
        this.head = head;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public static class HeadBean {
        /**
         * code : 00000000
         * description : �ɹ�
         * msg : �ɹ�
         * time : 2019-04-10 15:19:34
         * status : Y
         */

        private String code;
        private String description;
        private String msg;
        private String time;
        private String status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
