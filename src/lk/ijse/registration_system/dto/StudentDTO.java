package lk.ijse.registration_system.dto;

import java.util.Date;

public class StudentDTO {
    private String studentId;
    private String studentName;
    private String address;
    private Date dob;
    private int age;
    private String email;
    private String contactNo;

    public StudentDTO() {}

    public StudentDTO(String studentId) {
        this.studentId = studentId;
    }

    public StudentDTO(String studentId, String studentName, String address, Date dob, int age, String email, String contactNo) {
        this.setStudentId(studentId);
        this.setStudentName(studentName);
        this.setAddress(address);
        this.setDob(dob);
        this.setAge(age);
        this.setEmail(email);
        this.setContactNo(contactNo);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}
