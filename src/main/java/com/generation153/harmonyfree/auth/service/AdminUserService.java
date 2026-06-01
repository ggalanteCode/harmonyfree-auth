package com.generation153.harmonyfree.auth.service;

import com.generation153.harmonyfree.auth.security.enums.EnumStatus;

public interface AdminUserService {
	
	void updateUserStatus(Integer userId, EnumStatus status);

}
