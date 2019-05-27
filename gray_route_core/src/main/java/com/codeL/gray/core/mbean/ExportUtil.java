package com.codeL.gray.core.mbean;

import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.GrayType;

import javax.management.MBeanServer;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class ExportUtil {

    public static void exportClosed(MBeanServer mBeanServer) {
        new GrayMBeanExporter(mBeanServer).export(new GrayStatusMBean() {
            @Override
            public String getGrayStatus() {
                return GrayStatus.Close.getDescription();
            }

            @Override
            public String getGrayType() {
                return GrayType.U.name();
            }
        });
    }

    public static void export(MBeanServer mBeanServer, GrayStatus grayStatus, GrayType grayType) {
        new GrayMBeanExporter(mBeanServer).export(new GrayStatusMBeanImpl(grayStatus, grayType));
    }
}
