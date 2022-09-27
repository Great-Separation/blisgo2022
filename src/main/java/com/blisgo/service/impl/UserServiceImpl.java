package com.blisgo.service.impl;

import com.blisgo.domain.entity.User;
import com.blisgo.domain.mapper.DogamMapper;
import com.blisgo.domain.mapper.UserMapper;
import com.blisgo.domain.repository.impl.UserRepositoryImpl;
import com.blisgo.service.UserService;
import com.blisgo.web.dto.DogamDTO;
import com.blisgo.web.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepositoryImpl userRepository;

    private static int index = 0;
    private static final int limit = 24;

    public UserServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean addUser(UserDTO userDTO) {
        var user = UserMapper.INSTANCE.toEntity(userDTO);
        String default_profile_img = "https://ui-avatars.com/api/?background=random&name=" + user.getEmail();
        user = User.createUser(user, default_profile_img);
        userRepository.insertUser(user);
        userRepository.updateProfileImg(user, default_profile_img);
        return true;
    }

    @Override
    public UserDTO findUser(UserDTO userDTO) {
        var user = UserMapper.INSTANCE.toEntity(userDTO);
        var rs = userRepository.selectUser(user);
        return UserMapper.INSTANCE.toDTO(rs);

    }

//	@Override
//	public int emailCheck(UserDTO userDTO) {
//		var user = UserMapper.INSTANCE.toEntity(userDTO);
//		var rs = userRepository.emailCheck(user);
//		return rs;
//	}

    @Override
    public boolean modifyUserNickname(UserDTO userDTO) {
        var user = UserMapper.INSTANCE.toEntity(userDTO);
        return userRepository.updateNickname(user) > 0;
    }

    @Override
    public boolean removeUser(UserDTO userDTO) {
        var user = UserMapper.INSTANCE.toEntity(userDTO);
        userRepository.deleteAccount(user);
        return true;
    }

    @Override
    public boolean modifyUserPass(UserDTO userDTO, String newPass) {
        var user = UserMapper.INSTANCE.toEntity(userDTO);
        long result = userRepository.updatePassword(user, newPass);
        return result > 0;
    }

    @Override
    public List<DogamDTO> findDogam(UserDTO userDTO) {
        index = 0;
        var user = UserMapper.INSTANCE.toEntity(userDTO);
        var rs = userRepository.selectDogamList(user, index, limit);
        return DogamMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public List<DogamDTO> findDogamMore(UserDTO userDTO) {
        var user = UserMapper.INSTANCE.toEntity(userDTO);
        index += limit;

        // 더이상 조회되는 내용이 없을때의 오류 방지
        var rs = userRepository.selectDogamList(user, index, limit);
        return DogamMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public void modifyUserProfileImg(UserDTO userDTO, String profile_img_url) {
        var user = UserMapper.INSTANCE.toEntity(userDTO);
        userRepository.updateProfileImg(user, profile_img_url);
    }

    @Override
    public String findTermsOfAgreement() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("static/agreement.txt");

        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(in));
        Stream<String> streamOfString = new BufferedReader(inputStreamReader).lines();
        return streamOfString.collect(Collectors.joining());
    }

}
