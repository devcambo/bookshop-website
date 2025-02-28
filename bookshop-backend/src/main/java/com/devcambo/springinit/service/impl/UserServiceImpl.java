package com.devcambo.springinit.service.impl;

import com.devcambo.springinit.exception.InvalidSortFieldException;
import com.devcambo.springinit.exception.ResourceNotFoundException;
import com.devcambo.springinit.mapper.UserMapper;
import com.devcambo.springinit.model.dto.request.UserCreationDto;
import com.devcambo.springinit.model.dto.request.UserUpdateDto;
import com.devcambo.springinit.model.dto.response.UserResponseDto;
import com.devcambo.springinit.model.entity.User;
import com.devcambo.springinit.repo.UserRepo;
import com.devcambo.springinit.service.UserService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Page<UserResponseDto> readAll(
    Integer offset,
    Integer pageSize,
    String sortBy,
    String sortDir
  ) {
    if (!isValidSortField(sortBy)) {
      throw new InvalidSortFieldException("Invalid sort field");
    }
    Sort.Direction direction = sortDir.equalsIgnoreCase("ASC")
      ? Sort.Direction.ASC
      : Sort.Direction.DESC;
    PageRequest pageRequest = PageRequest.of(
      offset,
      pageSize,
      Sort.by(direction, sortBy)
    );
    Page<User> users = userRepo.findAll(pageRequest);
    return users.map(userMapper::toDto);
  }

  @Override
  public UserResponseDto readById(Long userId) {
    User user = getUserById(userId);
    return userMapper.toDto(user);
  }

  @Override
  public UserResponseDto create(UserCreationDto userCreationDto) {
    User user = userMapper.toEntity(userCreationDto);
    user.setRoles("ADMIN");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepo.save(user);
    return userMapper.toDto(savedUser);
  }

  @Override
  public UserResponseDto update(Long userId, UserUpdateDto userUpdateDto) {
    User existingUser = getUserById(userId);
    userMapper.updateFromDto(userUpdateDto, existingUser);
    User savedUser = userRepo.save(existingUser);
    return userMapper.toDto(savedUser);
  }

  @Override
  public void delete(Long userId) {
    User idToDelete = getUserById(userId);
    userRepo.deleteById(idToDelete.getUserId());
  }

  @Override
  public UserResponseDto profile(String email) {
    User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("profile", email));
    return userMapper.toDto(user);
  }

  private User getUserById(Long userId) {
    return userRepo
      .findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("user", userId));
  }

  private boolean isValidSortField(String field) {
    return Arrays.asList("userId", "email").contains(field);
  }
}
