package lk.ijse.registration_system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)

public class Login {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userId;
    private String userName;
    private String password;

    public Login() {}

    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Login(int userId, String userName, String password) {
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
        return "Login{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
