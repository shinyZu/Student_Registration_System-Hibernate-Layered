package lk.ijse.registration_system.dao.custom;

import lk.ijse.registration_system.dao.SuperDAO;
import lk.ijse.registration_system.entity.Program;

import java.util.ArrayList;

public interface ProgramDAO extends SuperDAO {
    boolean add(Program program);

    boolean update(Program program);

    boolean delete(Program program);

    String generateID();

    ArrayList<Program> getAll();

    ArrayList<String> getProgramNames();

    double getProgramFee(String programName);

    String getProgramID(String programName);

    boolean ifExist(Program program);
}
