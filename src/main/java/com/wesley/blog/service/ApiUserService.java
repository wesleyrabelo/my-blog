package com.wesley.blog.service;

import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.exception.EntityNotFoundException;
import com.wesley.blog.repository.ApiUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiUserService {
    private final ApiUserRepository apiUserRepository;

    @Autowired
    public ApiUserService(ApiUserRepository apiUserRepository) {
        this.apiUserRepository = apiUserRepository;
    }

    public ApiUser saveApiUser(ApiUser apiUser){
        return apiUserRepository.save(apiUser);
    }
    public ApiUser putApiUser(ApiUser apiUser){
        return apiUserRepository.save(apiUser);
    }
    public void deleteApiUserById(Long id){
        apiUserRepository.deleteById(id);
    }
    public ApiUser findApiUserByEmail(String email){
        return apiUserRepository.findByEmail(email).orElseThrow(
                EntityNotFoundException::new
        );
    }
    public ApiUser findApiUserById(Long id){
        return apiUserRepository.findById(id).orElseThrow(
                EntityNotFoundException::new
        );
    }

    public ApiUser findApiUserByName(String name){
     return apiUserRepository.findByName(name).orElseThrow(
             EntityNotFoundException::new
     );
    }

    public List<ApiUser> findAllApiUsers(){
        return apiUserRepository.findAll();
    }
}
