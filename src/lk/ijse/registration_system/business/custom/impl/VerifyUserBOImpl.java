package lk.ijse.registration_system.business.custom.impl;

import lk.ijse.registration_system.business.custom.VerifyUserBO;
import lk.ijse.registration_system.dao.DAOFactory;
import lk.ijse.registration_system.dao.SuperDAO;
import lk.ijse.registration_system.dao.custom.LoginDAO;
import lk.ijse.registration_system.dto.LoginDTO;
import lk.ijse.registration_system.entity.Login;

public class VerifyUserBOImpl implements VerifyUserBO {

    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public boolean isVerifiedUser(LoginDTO dto) {
        return loginDAO.isExist(new Login(dto.getUserName(),dto.getPassword()));
    }

    @Override
    public boolean signUpAsNewUser(LoginDTO dto) {
        return loginDAO.add(new Login(dto.getUserName(),dto.getPassword()));
    }

    @Override
    public boolean checkForPasswordAvailability(LoginDTO dto) {
        return loginDAO.isPasswordAvailable(new Login(dto.getUserName(),dto.getPassword()));
    }
}
