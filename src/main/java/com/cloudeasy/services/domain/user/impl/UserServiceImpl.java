package com.cloudeasy.services.domain.user.impl;

import com.cloudeasy.domain.aws.ec2.instance.data.analysis.SuccessFailureData;
import com.cloudeasy.domain.user.User;
import com.cloudeasy.enums.error.ErrorFor;
import com.cloudeasy.enums.error.UserError;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.model.UserModel;
import com.cloudeasy.repository.user.UserRepository;
import com.cloudeasy.services.domain.aws.ec2.instance.data.analysis.SuccessFailureDataService;
import com.cloudeasy.services.domain.user.UserService;
import com.cloudeasy.services.domain.user.dto.UserDTO;
import com.cloudeasy.services.utils.encryption.EncryptionUtility;
import com.cloudeasy.services.utils.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class implements User Service
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private SuccessFailureDataService successFailureDataService;

    /**
     * To add user
     * @param userDTO
     * @throws CustomException
     */
    public void save(UserDTO userDTO) throws CustomException {
        log.debug("Request to save user");
        CustomException customException = new CustomException();
        if (userDTO == null) {
            customException.addException(ErrorFor.NEED_ALL_DETAIL_ERR.getErrorFor(), UserError.NEED_ALL_DETAIL.getUserError());
            throw customException;
        }
        if (userDTO.getId() == null || userDTO.getId() != 0) userDTO.setId(Long.valueOf(0));

        customException = isValidUserDetails(userDTO);

        if (customException == null) customException = new CustomException();

        if (UserModel.userEmailIdMap.containsKey(userDTO.getEmailId())) {
            customException.addException(ErrorFor.EMAIL_ID_ERR.getErrorFor(), UserError.EMAIL_ID_EXISTS.getUserError());
        }
        if (UserModel.userMobileNumberMap.containsKey(userDTO.getMobileNumber())) {
            customException.addException(ErrorFor.MOBILE_NUMBER_ERR.getErrorFor(), UserError.MOBILE_NUMBER_EXISTS.getUserError());
        }
        if (customException.getExceptions().size() > 0) {
            log.debug("User is not valid");
            throw customException;
        }

        String encryptedPassword=bcryptEncoder.encode(userDTO.getPassword()!=null?userDTO.getPassword():"");
        userDTO.setPassword(encryptedPassword);

        ModelMapper modelMapper = new ModelMapper();
        User user=modelMapper.map(userDTO,User.class);
        user=userRepository.save(user);
        UserModel.userEmailIdMap.put(user.getEmailId(),user);
        UserModel.userMobileNumberMap.put(user.getMobileNumber(),user);

        //In future we will write code here to send email to verify his/her account

        ModelMapper mapper=new ModelMapper();
        SuccessFailureData successFailureData=new SuccessFailureData();
        successFailureData.setSuccess(0L);
        successFailureData.setFailure(0L);
        successFailureData.setUser(user);
        this.successFailureDataService.save(successFailureData);

    }

    public UserDTO findById(Long id) {
        if(id==null) return null;
        Optional<User> user=this.userRepository.findById(id);
        if(user.isPresent()) {
            ModelMapper mapper=new ModelMapper();
            UserDTO userDTO=mapper.map(user.get(),UserDTO.class);
            return userDTO;
        }
        return null;
    }

    /**
     * To find user by email id
     * @param emailId
     * @return {@link UserDTO}
     */
    public UserDTO findByEmailId(String emailId) {
        User user=this.userRepository.findByEmailId(emailId);
        if(user==null) return null;
        ModelMapper mapper=new ModelMapper();
        UserDTO userDTO=mapper.map(user,UserDTO.class);
        return userDTO;
    }


    /**
     * To validate user
     * @param userDTO
     * @return {@link CustomException}
     */
    private CustomException isValidUserDetails(UserDTO userDTO) {

        CustomException customException=new CustomException();

        if (userDTO.getFirstName() == null || userDTO.getFirstName().length() == 0) {
            customException.addException(ErrorFor.FIRST_NAME_ERR.getErrorFor(), UserError.FIRST_NAME_REQUIRED.getUserError());
        }

        if (userDTO.getLastName() == null || userDTO.getLastName().length() == 0) {
            customException.addException(ErrorFor.LAST_NAME_ERR.getErrorFor(), UserError.LAST_NAME_REQUIRED.getUserError());
        }

        if (userDTO.getEmailId() == null || userDTO.getEmailId().length() == 0) {
            customException.addException(ErrorFor.EMAIL_ID_ERR.getErrorFor(), UserError.EMAIL_ID_REQUIRED.getUserError());
        } else if (Validator.isValidEmail(userDTO.getEmailId()) == false) {
            customException.addException(ErrorFor.EMAIL_ID_ERR.getErrorFor(), UserError.INVALID_EMAIL_ID.getUserError());
        }

        if (userDTO.getMobileNumber() == null || userDTO.getMobileNumber().length() <= 0) {
            customException.addException(ErrorFor.MOBILE_NUMBER_ERR.getErrorFor(), UserError.MOBILE_NUMBER_REQUIRED.getUserError());
        } else if (Validator.isValidMobileNumber(userDTO.getMobileNumber()) == false) {
            customException.addException(ErrorFor.MOBILE_NUMBER_ERR.getErrorFor(), UserError.INVALID_MOBILE_NUMBER.getUserError());
        }
        boolean flag=false;
        if (userDTO.getPassword() == null || userDTO.getPassword().length() == 0) {
                customException.addException(ErrorFor.PASSWORD_ERR.getErrorFor(), UserError.PASSWORD_REQUIRED.getUserError());
                flag=true;
        }

        if (userDTO.getConfirmPassword() == null || userDTO.getConfirmPassword().length() == 0) {
            customException.addException(ErrorFor.CONFIRM_PASSWORD_ERR.getErrorFor(), UserError.CONFIRM_PASSWORD_REQUIRED.getUserError());
            flag=true;
        }

        if(!flag && userDTO.getPassword()!=null && userDTO.getPassword()!=null && !userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            customException.addException(ErrorFor.PASSWORD_ERR.getErrorFor(), UserError.PASSWORD_NOT_MATCH.getUserError());
            customException.addException(ErrorFor.CONFIRM_PASSWORD_ERR.getErrorFor(), UserError.PASSWORD_NOT_MATCH.getUserError());
        }

        if (customException.getExceptions().size() > 0) return customException;
        return null;

    }

}
