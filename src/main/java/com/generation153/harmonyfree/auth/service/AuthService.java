package com.generation153.harmonyfree.auth.service;

import com.generation153.harmonyfree.auth.entity.User;

public interface AuthService {
	
    void register(User user);
    void registerAdmin(User user);
    String login(String email, String password);

}
