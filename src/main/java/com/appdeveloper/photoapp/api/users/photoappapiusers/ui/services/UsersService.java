package com.appdeveloper.photoapp.api.users.photoappapiusers.ui.services;

import com.appdeveloper.photoapp.api.users.photoappapiusers.ui.rest.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
}
