package com.appdeveloper.photoapp.api.users.photoappapiusers.ui.services.implementations;

import com.appdeveloper.photoapp.api.users.photoappapiusers.ui.database.persistent.UserEntity;
import com.appdeveloper.photoapp.api.users.photoappapiusers.ui.database.repository.UserRepository;
import com.appdeveloper.photoapp.api.users.photoappapiusers.ui.rest.shared.UserDto;
import com.appdeveloper.photoapp.api.users.photoappapiusers.ui.services.UsersService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UsersServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEcryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity user = modelMapper.map(userDetails, UserEntity.class);


        userRepository.save(user);

        UserDto returnValue = modelMapper.map(user, UserDto.class);

        return returnValue;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         UserEntity userEntity = userRepository.findByEmail(username);

         if(userEntity == null) throw new UsernameNotFoundException(username);

         return new User(userEntity.getEmail(), userEntity.getEcryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
