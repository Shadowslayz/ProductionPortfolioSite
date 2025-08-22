package jianmejia.portfoliosite.password;

public class PasswordStorage {
    public static String Username= "admin";
    public static String Password = "THISFUCKINGPORTFOLIOISSOSTUPID"; // Change this to your desired password

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }
}
