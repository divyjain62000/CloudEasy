package com.cloudeasy.services.mail.dto;

import com.cloudeasy.dto.DataTransferObject;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class MailDTO extends DataTransferObject {

    private List<String> to;
    private String subject;
    private String body;
    Map<String,Object> emailMap;
    private String attachedFilename;
    private String originalFilename;
}
