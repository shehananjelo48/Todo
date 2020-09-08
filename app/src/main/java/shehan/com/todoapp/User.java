package shehan.com.todoapp;

import java.lang.ref.SoftReference;

public class User {
    private String fistName;
    private String lastName;
    private String age;
    private String phone;

    public User() {
    }

    public User(String fistName, String lastName, String age, String phone) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
