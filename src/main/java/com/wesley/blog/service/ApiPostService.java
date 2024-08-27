package com.wesley.blog.service;

import com.wesley.blog.entity.ApiPost;
import com.wesley.blog.entity.ApiUser;
import com.wesley.blog.exception.EntityNotFoundException;
import com.wesley.blog.repository.ApiPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiPostService {
    private final ApiPostRepository apiPostRepository;

    @Autowired
    public ApiPostService(ApiPostRepository apiPostRepository) {
        this.apiPostRepository = apiPostRepository;
    }

    public ApiPost saveApiPost(ApiPost apiPost){ return apiPostRepository.save(apiPost);}
    public ApiPost putApiPost(ApiPost apiPost){return apiPostRepository.save(apiPost);}
    public void deleteApiPostById(Long id){apiPostRepository.deleteById(id);}
    public ApiPost findApiPostById(Long id){
        return apiPostRepository.findById(id).orElseThrow(
            EntityNotFoundException::new
        );
    }
    public List<ApiPost> findApiPostByApiUser(ApiUser apiUser){
        return apiPostRepository.findByApiUser(apiUser);
    }
    public List<ApiPost> findAllApiPost() {return apiPostRepository.findAll();}
}
