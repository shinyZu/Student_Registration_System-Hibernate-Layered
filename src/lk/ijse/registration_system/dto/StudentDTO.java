package lk.ijse.registration_system.dto;

import lk.ijse.registration_system.entity.Registration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDTO {
    private String studentId;
    private String studentName;
    private String address;
    private Date dob;
    private int age;
    private String email;
    private String contactNo;

    private List<Registration> regDetails = new ArrayList<>();

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

    public StudentDTO(String studentId, String studentName, String address, Date dob, int age, String email, String contactNo, List<Registration> regDetails) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.address = address;
        this.dob = dob;
        this.age = age;
        this.email = email;
        this.contactNo = contactNo;
        this.regDetails = regDetails;
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

    public List<Registration> getRegDetails() {
        return regDetails;
    }

    public void setRegDetails(List<Registration> regDetails) {
        this.regDetails = regDetails;
    }

    /*@Override
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
    }*/

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
                ", regDetails=" + regDetails +
                '}';
    }
}
