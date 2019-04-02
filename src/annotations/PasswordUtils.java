package annotations;

import java.util.List;

public class PasswordUtils {

    @UseCase(id=47,description = "密码应该至少包含一位数字！")
    public boolean validatePassword(String password){
        return password.matches("\\w*\\d\\w*");
    }

    @UseCase(id = 48)
    public String encryptPassword(String password){
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 49,description = "新的密码不能是之前有的密码！")
    public boolean checkForNewPassword(List<String> prevPassword,String password){
        return !prevPassword.contains(password);
    }


}
