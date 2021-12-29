package lk.ijse.registration_system.view.tm;

public class RegistrationTM {
    private String studentId;
    private String programId;
    private String program;
    private String duration;
    private double programFee;
    private String dateOfRegistry;
    private double upfrontFee;

    public RegistrationTM() {}

    public RegistrationTM(String studentId, String programId, String program, String duration, double programFee, String dateOfRegistry, double upfrontFee) {
        this.setStudentId(studentId);
        this.setProgramId(programId);
        this.setProgram(program);
        this.setDuration(duration);
        this.setProgramFee(programFee);
        this.setDateOfRegistry(dateOfRegistry);
        this.setUpfrontFee(upfrontFee);
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

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
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

    public String getDateOfRegistry() {
        return dateOfRegistry;
    }

    public void setDateOfRegistry(String dateOfRegistry) {
        this.dateOfRegistry = dateOfRegistry;
    }

    public double getUpfrontFee() {
        return upfrontFee;
    }

    public void setUpfrontFee(double upfrontFee) {
        this.upfrontFee = upfrontFee;
    }

    @Override
    public String toString() {
        return "RegistrationTM{" +
                "studentId='" + studentId + '\'' +
                ", programId='" + programId + '\'' +
                ", program='" + program + '\'' +
                ", duration='" + duration + '\'' +
                ", programFee=" + programFee +
                ", dateOfRegistry='" + dateOfRegistry + '\'' +
                ", upfrontFee=" + upfrontFee +
                '}';
    }
}
