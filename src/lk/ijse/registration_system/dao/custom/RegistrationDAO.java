package lk.ijse.registration_system.dao.custom;

import lk.ijse.registration_system.dao.SuperDAO;
import lk.ijse.registration_system.entity.Registration;

public interface RegistrationDAO extends SuperDAO {

    boolean add(Registration registration);
}
