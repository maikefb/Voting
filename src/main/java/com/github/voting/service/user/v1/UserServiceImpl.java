package com.github.voting.service.user.v1;

import com.github.voting.dto.user.v1.UserCreateRequestDto;
import com.github.voting.mapper.user.v1.UserMapper;
import com.github.voting.repository.user.v1.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public void create(UserCreateRequestDto requestDto) {
        userRepository.save(userMapper.mapNewUser(requestDto));
    }
}
