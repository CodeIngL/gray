# 快速指南 #

## 1. 添加依赖 ##

### 1.1 Maven ###

maven依赖

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-framework-bom</artifactId>
                <version>${你的version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>    
            <dependency>
                <groupId>com.codeL</groupId>
                <artifactId>gray_route_bom</artifactId>
                <version>1.0-SNAPSHOT</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.codeL</groupId>
                <artifactId>gray_auto_detect_bom</artifactId>
                <version>1.0-SNAPSHOT</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>


dubbo端:

    <dependency>
        <groupId>com.codeL</groupId>
        <artifactId>gray_route_dubbo_support</artifactId>
	</dependency>

jms端:

    <dependency>
        <groupId>com.codeL</groupId>
        <artifactId>gray_route_jms_support</artifactId>
    </dependency>


自动激活检测,当存在相关jar包的时候进行相关技术栈灰度支持:

     <dependency>
        <groupId>com.codeL</groupId>
        <artifactId>gray_auto_conf</artifactId>
     </dependency>    

配置激活dubbo灰度功能:

    <dependency>
        <groupId>com.codeL</groupId>
        <artifactId>gray_auto_dubbo</artifactId>
    </dependency>
    
配置激活jms消费端灰度功能:

    <dependency>
        <groupId>com.codeL</groupId>
        <artifactId>gray_auto_jms_c</artifactId>
    </dependency>
    
配置激活jms生产端灰度功能:

    <dependency>
        <groupId>com.codeL</groupId>
        <artifactId>gray_auto_jms_p</artifactId>
    </dependency>

## 2. spring代码配置 ##

配置项

    gray.http.url=http://127.0.0.1:9000/admin
    
OK