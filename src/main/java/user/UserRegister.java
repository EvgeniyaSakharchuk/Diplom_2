package user;

public class UserRegister {
    private UserAuth userAuth;
    private String accessToken;
    private boolean success;

    public UserAuth getUser() {
        return userAuth;
    }

    public void setUser(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
