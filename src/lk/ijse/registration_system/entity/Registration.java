package lk.ijse.registration_system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Registration {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int regId;

    @Temporal(TemporalType.DATE)
    private Date dateOfReg;
    private double upfrontFee;

    @ManyToOne
    @JoinColumn(name = "studentId",referencedColumnName = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "programId",referencedColumnName = "programId")
    private Program program;

    public Registration() {}

    public Registration(Date dateOfReg, double upfrontFee, Program program) {
        this.dateOfReg = dateOfReg;
        this.upfrontFee = upfrontFee;
        this.program = program;
    }

    public Registration(Date dateOfReg, double upfrontFee, Student student, Program program) {
        this.dateOfReg = dateOfReg;
        this.upfrontFee = upfrontFee;
        this.student = student;
        this.program = program;
    }

    public Registration(int regId, Date dateOfReg, double upfrontFee, Student student, Program program) {
        this.setRegId(regId);
        this.setDateOfReg(dateOfReg);
        this.setUpfrontFee(upfrontFee);
        this.setStudent(student);
        this.setProgram(program);
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "regId=" + regId +
                ", dateOfReg=" + dateOfReg +
                ", upfrontFee=" + upfrontFee +
                ", student=" + student +
                ", program=" + program +
                '}';
    }
}
