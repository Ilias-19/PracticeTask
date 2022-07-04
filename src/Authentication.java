import java.util.Scanner;

public class Authentication {

    private static final String LOGIN = "ilyas";
    private static final String PASSWORD = "123";

    public static int authenticate() {
        Scanner s = new Scanner(System.in);


        boolean isLoginSuccess = false;

        for (int maxTry = 3; maxTry > 0 && !isLoginSuccess; maxTry--) {
            System.out.print("Login: ");
            var login = s.nextLine();

            System.out.print("Password: ");
            var password = s.nextLine();
            if (login.equals(LOGIN) && password.equals(PASSWORD)) {
                isLoginSuccess = true;
            }
        }
        return isLoginSuccess ? 0 : -1;
    }
}
