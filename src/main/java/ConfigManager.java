import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置管理工具类
 * 用于安全地加载和管理应用程序配置
 */
public class ConfigManager {
    private static final String CONFIG_FILE = "database.properties";
    private static Properties properties;
    
    static {
        loadConfiguration();
    }
    
    /**
     * 加载配置文件
     */
    private static void loadConfiguration() {
        properties = new Properties();
        
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException(
                    "配置文件未找到: " + CONFIG_FILE + "\n" +
                    "请确保在 src/main/resources/ 目录下存在 " + CONFIG_FILE + " 文件\n" +
                    "你可以复制 database.properties.example 文件并重命名为 database.properties"
                );
            }
            
            properties.load(input);
            validateConfiguration();
            System.out.println("✓ 配置文件加载成功");
            
        } catch (IOException e) {
            throw new RuntimeException("读取配置文件失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 验证配置完整性
     */
    private static void validateConfiguration() {
        String[] requiredKeys = {
            "database.url",
            "database.username", 
            "database.password",
            "database.driver"
        };
        
        for (String key : requiredKeys) {
            String value = properties.getProperty(key);
            if (value == null || value.trim().isEmpty()) {
                throw new RuntimeException("配置项缺失或为空: " + key);
            }
        }
    }
    
    /**
     * 获取数据库URL
     */
    public static String getDatabaseUrl() {
        return properties.getProperty("database.url");
    }
    
    /**
     * 获取数据库用户名
     */
    public static String getDatabaseUsername() {
        return properties.getProperty("database.username");
    }
    
    /**
     * 获取数据库密码
     */
    public static String getDatabasePassword() {
        return properties.getProperty("database.password");
    }
    
    /**
     * 获取数据库驱动类名
     */
    public static String getDatabaseDriver() {
        return properties.getProperty("database.driver");
    }
    
    /**
     * 获取配置属性值
     * @param key 配置键
     * @param defaultValue 默认值
     * @return 配置值或默认值
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * 获取配置属性值
     * @param key 配置键
     * @return 配置值
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
