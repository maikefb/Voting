package com.github.voting.service.user.v1;

import com.github.voting.domain.user.User;
import com.github.voting.dto.user.v1.UserCreateRequestDto;
import com.github.voting.exception.NotFoundException;
import com.github.voting.mapper.user.v1.UserMapper;
import com.github.voting.repository.user.v1.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ERROR_USER_NOT_FOUND = "Usuario não encontrado!";

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public void create(UserCreateRequestDto requestDto) {
        userRepository.save(userMapper.mapNewUser(requestDto));
    }

    @Override
    public User findByDocument(String cpfCnpj){
        return userRepository.findBycpfCnpj(cpfCnpj).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));
    }
}
