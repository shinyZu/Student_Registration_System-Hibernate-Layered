package lk.ijse.registration_system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Program {
    @Id
    private String programId;
    private String programName;
    private String duration;
    private double fee;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Registration> regDetails = new ArrayList<>();

    public Program() {}

    public Program(String programId) {
        this.programId = programId;
    }

    public Program(String programId, String programName, String duration, double fee) {
        this.programId = programId;
        this.programName = programName;
        this.duration = duration;
        this.fee = fee;
    }

    public Program(String programId, String programName, String duration, double fee, List<Registration> regDetails) {
        this.setProgramId(programId);
        this.setProgramName(programName);
        this.setDuration(duration);
        this.setFee(fee);
        this.setRegDetails(regDetails);
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public List<Registration> getRegDetails() {
        return regDetails;
    }

    public void setRegDetails(List<Registration> regDetails) {
        this.regDetails = regDetails;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programId='" + programId + '\'' +
                ", programName='" + programName + '\'' +
                ", duration='" + duration + '\'' +
                ", fee=" + fee +
                '}';
    }
}
