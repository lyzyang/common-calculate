package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ResourceUtil {

    private static final Logger log = LoggerFactory.getLogger(ResourceUtil.class);

    public static InputStream getResourceAsStream(String externalFilePath) throws IOException {
        // 尝试外部文件
        File externalFile = new File(externalFilePath);
        if (externalFile.exists() && externalFile.isFile()) {
            log.info("读取外部文件");
            return new FileInputStream(externalFile);
        }

        // 尝试从JAR内部
        log.info("读取JAR内部文件");
        InputStream internalStream = null;

        // 尝试1: 当前线程的上下文类加载器
        ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
        if (ctxClassLoader != null) {
            internalStream = ctxClassLoader.getResourceAsStream(externalFilePath);
            if (internalStream != null) return internalStream;
        }

        // 尝试2: 系统类加载器
        internalStream = ClassLoader.getSystemResourceAsStream(externalFilePath);
        if (internalStream != null) return internalStream;

        // 尝试3: 当前类的类加载器
        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        if (classLoader != null) {
            internalStream = classLoader.getResourceAsStream(externalFilePath);
            if (internalStream != null) return internalStream;
        }

        throw new FileNotFoundException("文件未找到: " + externalFilePath);
    }
}