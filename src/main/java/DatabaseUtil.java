import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://yourlocalhost/student_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf8&useUnicode=true";
    private static final String USERNAME = "yourusername"; // 请替换为实际用户名
    private static final String PASSWORD = "yourpassword"; // 请替换为实际密码
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC驱动加载成功");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC驱动未找到: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
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