package lk.ijse.registration_system.dto;

import java.util.Date;

public class RegistrationDTO {
    private int regId;
    private Date dateOfReg;
    private double upfrontFee;
    private String studentId;
    private String programId;

    public RegistrationDTO() {}

    public RegistrationDTO(Date dateOfReg, double upfrontFee, String studentId, String programId) {
        this.setDateOfReg(dateOfReg);
        this.setUpfrontFee(upfrontFee);
        this.setStudentId(studentId);
        this.setProgramId(programId);
    }

    public RegistrationDTO(int regId, Date dateOfReg, double upfrontFee, String studentId, String programId) {
        this.setRegId(regId);
        this.setDateOfReg(dateOfReg);
        this.setUpfrontFee(upfrontFee);
        this.setStudentId(studentId);
        this.setProgramId(programId);
    }


    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    public Date getDateOfReg() {
        return dateOfReg;
    }

    public void setDateOfReg(Date dateOfReg) {
        this.dateOfReg = dateOfReg;
    }

    public double getUpfrontFee() {
        return upfrontFee;
    }

    public void setUpfrontFee(double upfrontFee) {
        this.upfrontFee = upfrontFee;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "RegistrationDTO{" +
                "regId=" + regId +
                ", dateOfReg=" + dateOfReg +
                ", upfrontFee=" + upfrontFee +
                ", studentId='" + studentId + '\'' +
                ", programId='" + programId + '\'' +
                '}';
    }
}
