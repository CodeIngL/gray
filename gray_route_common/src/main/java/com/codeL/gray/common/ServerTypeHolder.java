package com.codeL.gray.common;

import java.util.Objects;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class ServerTypeHolder {

    public ServerTypeHolder(String servertype) {
        this.servertype = servertype;
    }

    private String servertype;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerTypeHolder that = (ServerTypeHolder) o;
        return Objects.equals(servertype, that.servertype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(servertype);
    }
}
