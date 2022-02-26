package com.cloudeasy.web.rest.auth;

import java.util.Objects;

import com.cloudeasy.config.JwtTokenUtil;
import com.cloudeasy.enums.error.GlobalError;
import com.cloudeasy.enums.messages.SuccessMessage;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.model.auth.JwtRequest;
import com.cloudeasy.model.auth.JwtResponse;
import com.cloudeasy.response.ActionResponse;
import com.cloudeasy.services.auth.JwtUserDetailsService;
import com.cloudeasy.services.domain.user.UserService;
import com.cloudeasy.services.domain.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final UserDTO userDTO=this.userService.findByEmailId(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails,userDTO);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<ActionResponse> save(@RequestBody UserDTO userDTO) {
        ActionResponse response=new ActionResponse();
        try {
            this.userService.save(userDTO);
            response.setSuccessful(true);
            response.setException(false);
            response.setMessage(SuccessMessage.USER_ADDED.getMessage());
            return ResponseEntity.ok().body(response);
        } catch (CustomException customException) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(customException.getExceptions());
            return ResponseEntity.ok().body(response);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(GlobalError.INTERNAL_SERVER_ERROR);
            return ResponseEntity.internalServerError().body(response);
        }

    }

}
