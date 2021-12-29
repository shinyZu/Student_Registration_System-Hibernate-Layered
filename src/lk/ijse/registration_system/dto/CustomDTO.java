package lk.ijse.registration_system.dto;

import java.util.Date;

public class CustomDTO {
    private String studentId;
    private String studentName;
    private String address;
    private Date dob;
    private int age;
    private String email;
    private String contactNo;

    private String programId;
    private String programName;
    private String duration;
    private double programFee;
    private double upfrontFee;
    private Date dateOfRegistry;

    public CustomDTO() {}

    public CustomDTO(String studentId, String programName, double upfrontFee) {
        this.setStudentId(studentId);
        this.setProgramName(programName);
        this.setUpfrontFee(upfrontFee);
    }

    public CustomDTO(String studentId, String studentName, String address, Date dob, int age, String email, String contactNo, String programName, double upfrontFee) {
        this.setStudentId(studentId);
        this.setStudentName(studentName);
        this.setAddress(address);
        this.setDob(dob);
        this.setAge(age);
        this.setEmail(email);
        this.setContactNo(contactNo);
        this.setProgramName(programName);
        this.setUpfrontFee(upfrontFee);
    }

    public CustomDTO(String studentId, String studentName, String address, Date dob, int age, String email, String contactNo, String programId, String programName, double upfrontFee) {
        this.setStudentId(studentId);
        this.setStudentName(studentName);
        this.setAddress(address);
        this.setDob(dob);
        this.setAge(age);
        this.setEmail(email);
        this.setContactNo(contactNo);
        this.setProgramId(programId);
        this.setProgramName(programName);
        this.setUpfrontFee(upfrontFee);
    }


    public CustomDTO(String studentId, String programId, String programName, String duration, double programFee, Date dateOfRegistry, double upfrontFee) {
        this.studentId = studentId;
        this.programId = programId;
        this.programName = programName;
        this.duration = duration;
        this.programFee = programFee;
        this.dateOfRegistry = dateOfRegistry;
        this.upfrontFee = upfrontFee;
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

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getProgramFee() {
        return programFee;
    }

    public void setProgramFee(double programFee) {
        this.programFee = programFee;
    }

    public double getUpfrontFee() {
        return upfrontFee;
    }

    public void setUpfrontFee(double upfrontFee) {
        this.upfrontFee = upfrontFee;
    }

    public Date getDateOfRegistry() {
        return dateOfRegistry;
    }

    public void setDateOfRegistry(Date dateOfRegistry) {
        this.dateOfRegistry = dateOfRegistry;
    }

    @Override
    public String toString() {
        return "CustomDTO{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", programId='" + programId + '\'' +
                ", programName='" + programName + '\'' +
                ", duration='" + duration + '\'' +
                ", programFee=" + programFee +
                ", upfrontFee=" + upfrontFee +
                ", dateOfRegistry='" + dateOfRegistry + '\'' +
                '}';
    }
}
