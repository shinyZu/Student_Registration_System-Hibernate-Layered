package lk.ijse.registration_system.dto;

public class LoginDTO {
    private int userId;
    private String userName;
    private String password;

    public LoginDTO() {}

    public LoginDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginDTO(int userId, String userName, String password) {
        this.setUserId(userId);
        this.setUserName(userName);
        this.setPassword(password);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
