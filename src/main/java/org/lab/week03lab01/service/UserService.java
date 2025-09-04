package org.lab.week03lab01.service;

import org.lab.week03lab01.model.User;
import org.lab.week03lab01.model.CreateUserDTO;
import org.lab.week03lab01.model.UserNoPasswordDTO;
import org.lab.week03lab01.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserNoPasswordDTO findById(Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserNoPasswordDTO.class);
    }

    public List<UserNoPasswordDTO> findAll(){
        var users = userRepository.findAll();

        var list = new ArrayList<UserNoPasswordDTO>();
        for (var user : users) {
            list.add(modelMapper.map(user, UserNoPasswordDTO.class));
        }

        return list;
    }

    public UserNoPasswordDTO createUser(CreateUserDTO dto) {
        User newUser = new User();
        modelMapper.map(dto, newUser);
        userRepository.save(newUser);
        return modelMapper.map(newUser, UserNoPasswordDTO.class);
    }
}
