import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    
    static {
        loadJDBCDriver();
    }
    
    private static void loadJDBCDriver() {
        try {
            Class.forName(ConfigManager.getDatabaseDriver());
            System.out.println("✓ MySQL JDBC驱动加载成功");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL JDBC驱动未找到: " + e.getMessage());
            throw new RuntimeException("无法加载数据库驱动", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            ConfigManager.getDatabaseUrl(),
            ConfigManager.getDatabaseUsername(),
            ConfigManager.getDatabasePassword()
        );
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("关闭数据库连接失败: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✓ 数据库连接测试成功");
            return true;
        } catch (SQLException e) {
            System.err.println("✗ 数据库连接测试失败: " + e.getMessage());
            return false;
        }
    }
}