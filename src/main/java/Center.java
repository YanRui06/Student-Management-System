import java.util.List;
import java.util.Scanner;

public class Center {
    private static StudentDAO studentDAO = new StudentDAO();
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 测试数据库连接
        if (!DatabaseUtil.testConnection()) {
            System.err.println("数据库连接失败，程序退出");
            return;
        }
        
        // 初始化数据库表
        studentDAO.createTable();
        
        while (true) {
            System.out.println("\n========== 学生管理系统 ==========");
            System.out.println("1. 添加学生");
            System.out.println("2. 删除学生");
            System.out.println("3. 修改学生");
            System.out.println("4. 查询学生");
            System.out.println("5. 退出系统");
            System.out.println("===============================");
            System.out.print("请选择操作：");
            
            String choose = sc.next();
            switch (choose) {
                case "1" -> addStudent();
                case "2" -> deleteStudent();
                case "3" -> updateStudent();
                case "4" -> searchStudent();
                case "5" -> {
                    System.out.println("退出系统，再见！");
                    System.exit(0);
                }
                default -> System.out.println("没有这个选项，请重新选择");
            }
        }
    }

    public static void addStudent() {
        Student student = new Student();
        Scanner sc = new Scanner(System.in);
        
        // 输入ID
        while (true) {
            System.out.print("请输入学生ID：");
            String ID = sc.next();
            if (!ValidationUtil.isValidID(ID)) {
                System.out.println("学生ID格式不正确，请重新输入");
                continue;
            }
            if (studentDAO.checkStudentExists(ID)) {
                System.out.println("学生ID已存在，请重新输入");
            } else {
                student.setID(ID);
                break;
            }
        }
        
        // 输入姓名
        while (true) {
            System.out.print("请输入学生姓名：");
            String name = sc.next();
            if (ValidationUtil.isValidName(name)) {
                student.setName(name);
                break;
            } else {
                System.out.println("姓名格式不正确，请重新输入");
            }
        }
        
        // 输入年龄
        while (true) {
            System.out.print("请输入学生年龄：");
            try {
                int age = sc.nextInt();
                if (ValidationUtil.isValidAge(age)) {
                    student.setAge(age);
                    break;
                } else {
                    System.out.println("年龄必须在1-150之间，请重新输入");
                }
            } catch (Exception e) {
                System.out.println("请输入有效的年龄数字");
                sc.nextLine(); // 清除无效输入
            }
        }
        
        // 输入地址
        sc.nextLine(); // 消费换行符
        while (true) {
            System.out.print("请输入学生家庭住址：");
            String address = sc.nextLine();
            if (ValidationUtil.isValidAddress(address)) {
                student.setAddress(address);
                break;
            } else {
                System.out.println("地址格式不正确，请重新输入");
            }
        }
        
        if (studentDAO.addStudent(student)) {
            System.out.println("✓ 添加成功");
        } else {
            System.out.println("✗ 添加失败");
        }
    }

    public static void deleteStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入要删除的学生ID：");
        String ID = sc.next();
        
        if (studentDAO.checkStudentExists(ID)) {
            if (studentDAO.deleteStudent(ID)) {
                System.out.println("✓ ID为" + ID + "的学生删除成功");
            } else {
                System.out.println("✗ 删除失败");
            }
        } else {
            System.out.println("✗ 学生ID不存在");
        }
    }

    public static void updateStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入要修改学生的ID：");
        String ID = sc.next();
        
        Student student = studentDAO.findStudentById(ID);
        if (student == null) {
            System.out.println("✗ 学生不存在");
            return;
        }
        
        System.out.println("\n当前学生信息：");
        System.out.println("ID: " + student.getID());
        System.out.println("姓名: " + student.getName());
        System.out.println("年龄: " + student.getAge());
        System.out.println("地址: " + student.getAddress());
        
        System.out.print("\n请输入您想要修改的信息(姓名/年龄/家庭地址)：");
        String info = sc.next();
        
        switch (info) {
            case "姓名":
                while (true) {
                    System.out.print("请输入新的姓名：");
                    String name = sc.next();
                    if (ValidationUtil.isValidName(name)) {
                        student.setName(name);
                        break;
                    } else {
                        System.out.println("姓名格式不正确，请重新输入");
                    }
                }
                break;
            case "年龄":
                while (true) {
                    System.out.print("请输入新的年龄：");
                    try {
                        int age = sc.nextInt();
                        if (ValidationUtil.isValidAge(age)) {
                            student.setAge(age);
                            break;
                        } else {
                            System.out.println("年龄必须在1-150之间，请重新输入");
                        }
                    } catch (Exception e) {
                        System.out.println("请输入有效的年龄数字");
                        sc.nextLine(); // 清除无效输入
                    }
                }
                break;
            case "家庭地址":
                sc.nextLine(); // 消费换行符
                while (true) {
                    System.out.print("请输入新的家庭地址：");
                    String address = sc.nextLine();
                    if (ValidationUtil.isValidAddress(address)) {
                        student.setAddress(address);
                        break;
                    } else {
                        System.out.println("地址格式不正确，请重新输入");
                    }
                }
                break;
            default:
                System.out.println("✗ 无效的修改选项");
                return;
        }
        
        if (studentDAO.updateStudent(student)) {
            System.out.println("✓ 修改成功");
        } else {
            System.out.println("✗ 修改失败");
        }
    }

    public static void searchStudent() {
        List<Student> students = studentDAO.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("当前无学生数据");
            return;
        }
        
        System.out.println("\n=================== 学生信息列表 ===================");
        System.out.printf("%-15s %-10s %-5s %-20s%n", "学生ID", "姓名", "年龄", "家庭地址");
        System.out.println("---------------------------------------------------");
        
        for (Student student : students) {
            System.out.printf("%-15s %-10s %-5d %-20s%n", 
                student.getID(), 
                student.getName(), 
                student.getAge(), 
                student.getAddress());
        }
        System.out.println("===================================================");
        System.out.println("共找到 " + students.size() + " 条学生记录");
    }
}