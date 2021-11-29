package com.appdeveloper.photoapp.api.users.photoappapiusers.ui.database.repository;

import com.appdeveloper.photoapp.api.users.photoappapiusers.ui.database.persistent.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);



}
