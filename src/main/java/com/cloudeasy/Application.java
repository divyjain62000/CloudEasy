package com.cloudeasy;

import com.cloudeasy.domain.os.OperatingSystem;
import com.cloudeasy.domain.user.User;
import com.cloudeasy.model.OperatingSystemModel;
import com.cloudeasy.model.UserModel;
import com.cloudeasy.repository.os.OperatingSystemRepository;
import com.cloudeasy.repository.user.UserRepository;
import com.cloudeasy.services.os.dto.OperatingSystemResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

	@Autowired
	private OperatingSystemRepository operatingSystemRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	void populateDS() {

		ModelMapper mapper=new ModelMapper();

		//poplate OperatingSystem related DS
		List<OperatingSystem> operatingSystemList=this.operatingSystemRepository.findAll();
		operatingSystemList.forEach((operatingSystem)->{
			OperatingSystemResponseDTO operatingSystemResponseDTO=mapper.map(operatingSystem,OperatingSystemResponseDTO.class);
			OperatingSystemModel.operatingSystemList.add(operatingSystemResponseDTO);
			OperatingSystemModel.operatingSystemIdMap.put(operatingSystemResponseDTO.getId(),operatingSystemResponseDTO);
		});

		//populate User related DS
		List<User> userList=this.userRepository.findAll();
		userList.forEach((user)->{
			String mobileNumber= user.getMobileNumber();
			String emailId= user.getEmailId();
			UserModel.userMobileNumberMap.put(mobileNumber,user);
			UserModel.userEmailIdMap.put(emailId,user);
		});

	}

}
