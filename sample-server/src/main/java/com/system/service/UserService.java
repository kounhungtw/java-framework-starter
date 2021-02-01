package com.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.dto.LoginInfoDTO;
import com.system.dto.LoginResultDTO;
import com.system.entity.User;
import com.system.enums.ErrorMessageEnum;
import com.system.exception.BusinessException;
import com.system.exception.RequiredFieldEmptyException;
import com.system.exception.ResourceNotFoundException;
import com.system.jwt.JwtService;
import com.system.repository.UserRepository;
import com.system.util.SysUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	private User getByLoginId(String loginId) throws ResourceNotFoundException {
		return userRepository.findByLoginId(loginId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("No user [%s] found", loginId)));
	}
	
	public LoginResultDTO login(LoginInfoDTO loginInfoDTO)
			throws RequiredFieldEmptyException, ResourceNotFoundException, BusinessException {
		
		if (loginInfoDTO.getUserName() == null || loginInfoDTO.getUserName().isEmpty()
				|| loginInfoDTO.getPassword() == null || loginInfoDTO.getPassword().isEmpty()) {
			String error = "User name or password cannot be empty";
			throw new RequiredFieldEmptyException(error);
		}
		User user = getByLoginId(loginInfoDTO.getUserName());
		
		if (!user.getPassword().equals(SysUtil.MD5Digest(loginInfoDTO.getPassword()))) {
			throw new BusinessException(ErrorMessageEnum.Incorrect_Password);
		}
		
		LoginResultDTO loginResultDTO = new LoginResultDTO();
		loginResultDTO.setToken(jwtService.createUserJWT(user.getLoginId()));
		loginResultDTO.setUserId(user.getId());
		loginResultDTO.setUserName(user.getLoginId());
		return loginResultDTO;
	}
}
