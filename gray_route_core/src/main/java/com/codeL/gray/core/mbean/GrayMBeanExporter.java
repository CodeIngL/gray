package com.codeL.gray.core.mbean;

import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.*;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
class GrayMBeanExporter {

    private static final String O_NAME = "com.codeL.gray.jmx:type=GrayStatus";


    private MBeanServer mBeanServer;

    GrayMBeanExporter(MBeanServer mBeanServer) {
        this.mBeanServer = mBeanServer;
    }

    boolean export(GrayStatusMBean grayStatusMBean) {
        try {
            ObjectName objectName = new ObjectName(O_NAME);
            boolean registered = mBeanServer.isRegistered(objectName);
            if (registered) {
                mBeanServer.unregisterMBean(objectName);
            }
            RequiredModelMBean mbean = new RequiredModelMBean();
            mbean.setManagedResource(grayStatusMBean, "objectReference");
            ModelMBeanOperationInfo getGrayStatus = new ModelMBeanOperationInfo("get gray status", grayStatusMBean
                    .getClass().getMethod("getGrayStatus"));
            ModelMBeanOperationInfo getGrayType = new ModelMBeanOperationInfo("get gray type", grayStatusMBean
                    .getClass().getMethod("getGrayType"));

            ModelMBeanInfo mbeanInfo = new ModelMBeanInfoSupport("GrayStatusMBean", "show gray info",
                    null, null, new ModelMBeanOperationInfo[]{getGrayStatus, getGrayType}, null);
            mbean.setModelMBeanInfo(mbeanInfo);
            mBeanServer.registerMBean(mbean, objectName);
            return true;
        } catch (Exception e) {
            log.error("mbean export has some thing error", e);
        }
        return false;
    }
}
