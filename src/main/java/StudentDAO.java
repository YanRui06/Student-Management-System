import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS students (
                id VARCHAR(50) PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                age INT NOT NULL,
                address VARCHAR(200) NOT NULL
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
            """;
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✓ 学生表初始化成功");
        } catch (SQLException e) {
            System.err.println("✗ 创建学生表失败: " + e.getMessage());
        }
    }
    
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (id, name, age, address) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getID());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getAddress());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("添加学生失败: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("删除学生失败: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, age = ?, address = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setString(3, student.getAddress());
            pstmt.setString(4, student.getID());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("更新学生信息失败: " + e.getMessage());
            return false;
        }
    }
    
    public Student findStudentById(String id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Student(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("address")
                );
            }
        } catch (SQLException e) {
            System.err.println("查询学生失败: " + e.getMessage());
        }
        return null;
    }
    
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Student student = new Student(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("address")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("查询所有学生失败: " + e.getMessage());
        }
        return students;
    }
    
    public boolean checkStudentExists(String id) {
        return findStudentById(id) != null;
    }
}