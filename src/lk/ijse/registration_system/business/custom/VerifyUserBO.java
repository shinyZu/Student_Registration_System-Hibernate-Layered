package lk.ijse.registration_system.business.custom;

import lk.ijse.registration_system.business.SuperBO;
import lk.ijse.registration_system.dto.LoginDTO;

public interface VerifyUserBO extends SuperBO {
    boolean isVerifiedUser(LoginDTO loginDTO);

    boolean signUpAsNewUser(LoginDTO loginDTO);

    boolean checkForPasswordAvailability(LoginDTO loginDTO);
}
