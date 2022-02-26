package com.cloudeasy.web.rest.domain.user;

import com.cloudeasy.enums.error.GlobalError;
import com.cloudeasy.enums.messages.SuccessMessage;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.response.ActionResponse;
import com.cloudeasy.services.domain.user.UserService;
import com.cloudeasy.services.domain.user.dto.UserDTO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResources {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<ActionResponse> save(@RequestBody UserDTO userDTO) {
//        ActionResponse response=new ActionResponse();
//        try {
//            this.userService.save(userDTO);
//            response.setSuccessful(true);
//            response.setException(false);
//            response.setMessage(SuccessMessage.USER_ADDED.getMessage());
//            return ResponseEntity.ok().body(response);
//        } catch (CustomException customException) {
//            response.setSuccessful(false);
//            response.setException(true);
//            response.setResult(customException.getExceptions());
//            return ResponseEntity.ok().body(response);
//        }
//        catch (Exception exception) {
//            exception.printStackTrace();
//            response.setSuccessful(false);
//            response.setException(true);
//            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
//            return ResponseEntity.internalServerError().body(response);
//        }
//
//    }

}
