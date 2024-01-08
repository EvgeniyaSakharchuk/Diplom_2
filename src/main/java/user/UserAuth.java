package user;
import org.apache.commons.lang3.RandomStringUtils;

public class UserAuth {
    private String name;
    private String password;
    private String email;
    public static UserAuth usersRandomCreate() {
        UserAuth userAuth = new UserAuth();
        String randomName = RandomStringUtils.randomAlphabetic(15);
        userAuth.setEmail(randomName.toLowerCase() + "@yandex.ru");
        userAuth.setName(randomName.toLowerCase());
        userAuth.setPassword("5r6t7y8u");
        return userAuth;
    }

    public UserAuth(String email, String password, String name) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public UserAuth() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
