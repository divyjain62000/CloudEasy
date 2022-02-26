package com.cloudeasy.web.rest.domain.instance.access;

import com.cloudeasy.enums.messages.FailureMessage;
import com.cloudeasy.exceptions.CustomException;
import com.cloudeasy.response.ActionResponse;
import com.cloudeasy.services.domain.aws.ec2.instance.access.InstanceAccessService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/instance")
public class InstanceAccessResources {

    @Autowired
    private InstanceAccessService instanceAccessService;

    @PostMapping("/upload-file")
    public ResponseEntity<ActionResponse> uploadFile(@RequestParam("fileToUpload")MultipartFile file,@RequestParam("instanceId")Long instanceId) {
        ActionResponse response=new ActionResponse();
            try {
                boolean successful=this.instanceAccessService.uploadFile(file,instanceId);
                response.setSuccessful(successful);
                response.setException(!successful);
                response.setResult(null);
                response.setMessage(null);
            }catch (CustomException e) {
                response.setSuccessful(false);
                response.setException(true);
                response.setResult(e.getExceptions());
                response.setMessage(null);
            } catch (Exception e) {
            e.printStackTrace();
                response.setSuccessful(false);
                response.setException(true);
                response.setResult(e.getMessage());
                response.setMessage(null);
                return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok().body(response);
    }


    @ResponseBody
    @GetMapping("/download")
    public void downloadFile(@RequestParam("instanceId") Long instanceId,@RequestParam("filePath")String filePath, HttpServletResponse httpServletResponse) throws JSchException, SftpException, IOException, CustomException {
        ByteArrayInputStream file=this.instanceAccessService.downloadFile(instanceId,filePath);
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename="+filePath);
        IOUtils.copy(file, httpServletResponse.getOutputStream());
    }

    @RequestMapping(value = "/upload/static-website", consumes = {"multipart/*"},method = RequestMethod.POST)
    public ResponseEntity<ActionResponse> uploadStaticWebsite(@RequestParam("fileToUpload")MultipartFile file,@RequestParam("instanceId")Long instanceId) {
        ActionResponse response=new ActionResponse();
        try {
            boolean successful=this.instanceAccessService.uploadStaticWebsite(file,instanceId);
            response.setSuccessful(successful);
            response.setException(!successful);
            response.setResult(null);
            response.setMessage(null);
        }catch (CustomException e) {
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getExceptions());
            response.setMessage(null);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccessful(false);
            response.setException(true);
            response.setResult(e.getMessage());
            response.setMessage(null);
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok().body(response);
    }




}
