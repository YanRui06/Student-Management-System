public class ValidationUtil {
    
    public static boolean isValidAge(int age) {
        return age > 0 && age <= 150;
    }
    
    public static boolean isValidID(String id) {
        return id != null && !id.trim().isEmpty() && id.length() <= 50;
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 100;
    }
    
    public static boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty() && address.length() <= 200;
    }
}