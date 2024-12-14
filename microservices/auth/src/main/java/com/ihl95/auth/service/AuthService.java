package com.ihl95.auth.service;

import org.springframework.stereotype.Service;

@Service
public interface AuthService {

  public String authenticate(String username, String password);
  
}
