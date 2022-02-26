package com.cloudeasy.web.rest.domain.instance.configure;

import com.cloudeasy.enums.error.GlobalError;
import com.cloudeasy.enums.messages.SuccessMessage;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.response.ActionResponse;
import com.cloudeasy.services.domain.aws.ec2.instance.configure.InstanceConfigureService;
import com.cloudeasy.services.domain.aws.ec2.instance.configure.dto.InstanceConfigurationDTO;
import com.jcraft.jsch.JSchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/configure-instance")
@Slf4j
public class InstanceConfigureResources {

    @Autowired
    private InstanceConfigureService instanceConfigureService;

    @GetMapping("/databases")
    public ResponseEntity<ActionResponse> getAllDatabase() {
        Map<String,String> databases=this.instanceConfigureService.getAllDatabase();
        ActionResponse response=new ActionResponse();
        response.setSuccessful(true);
        response.setException(false);
        response.setResult(databases);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/compilers-interpreters")
    public ResponseEntity<ActionResponse> getAllCompilerAndInterpreter() {
        Map<String,String> compilersAndInterpreters=this.instanceConfigureService.getAllCompilerAndInterpreter();
        ActionResponse response=new ActionResponse();
        response.setSuccessful(true);
        response.setException(false);
        response.setResult(compilersAndInterpreters);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/install-databases")
    public ResponseEntity<ActionResponse> installDatabases(@RequestBody InstanceConfigurationDTO instanceConfigurationDTO) {
        ActionResponse response=new ActionResponse();
        log.debug("Rest request to install database");
        try {
            this.instanceConfigureService.installDatabases(instanceConfigurationDTO);
            response.setSuccessful(true);
            response.setException(false);
            response.setResult(null);
            response.setMessage(SuccessMessage.DATABASE_INSTALLED.getMessage());
        } catch (CustomException e) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getExceptions());
            response.setMessage(null);
        }catch (Exception e) {
            e.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(null);
            response.setMessage(GlobalError.INTERNAL_SERVER_ERROR.getGlobalError());
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/install-compiler-interpreter")
    public ResponseEntity<ActionResponse> installCompilerOrInterpreter(@RequestBody InstanceConfigurationDTO instanceConfigurationDTO) {
        log.debug("Rest request to install compiler or interpreter");
        ActionResponse response=new ActionResponse();
        try {
            this.instanceConfigureService.installCompilersAndInterpreters(instanceConfigurationDTO);
            response.setSuccessful(true);
            response.setException(false);
            response.setResult(null);
            response.setMessage(SuccessMessage.COMPILER_AND_INTERPRETER_INSTALLED.getMessage());
        } catch (CustomException e) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getExceptions());
            response.setMessage(null);
        }catch (Exception e) {
            e.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(null);
            response.setMessage(GlobalError.INTERNAL_SERVER_ERROR.getGlobalError());
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/before-upload")
    public ResponseEntity<ActionResponse> configureServer(@RequestBody InstanceConfigurationDTO instanceConfigurationDTO) {
        log.debug("Rest request to install compiler or interpreterconfigure");
        ActionResponse response=new ActionResponse();
        try {
            boolean successful=this.instanceConfigureService.initConfigureInstance(instanceConfigurationDTO);
            response.setSuccessful(successful);
            response.setException(false);
            response.setResult(null);
            response.setMessage(SuccessMessage.CONFIGURED_SUCCESSFULLY.getMessage());
        } catch (CustomException e) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getExceptions());
            response.setMessage(null);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(null);
            response.setMessage(GlobalError.INTERNAL_SERVER_ERROR.getGlobalError());
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok().body(response);
    }

}
