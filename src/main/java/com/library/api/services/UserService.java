package com.library.api.services;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.dto.*;
import com.library.api.event.OnUserPasswordSendEvent;
import com.library.api.exception.ResourceExistException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Role;
import com.library.api.model.User;
import com.library.api.repository.BookRequestRepository;
import com.library.api.repository.UserRepository;
import com.library.api.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BookRequestRepository bookRequestRepository;
    private final ApplicationEventPublisher eventPublisher;
    /**
     * @param dto student registration dto
     * @return response with user
     * @throws ResourceExistException when user not found in database
     */
    public Response register(StudentRegistrationDto dto) throws ResourceExistException {
        User user = modelMapper.map(dto, User.class);
        validateUser(user.getUsername(), user.getEmail());
        user.setRole(Role.ROLE_STUDENT.name());
        return getRegisterUserResponse(user);
    }

    public Response register(LibrarianRegistrationDto dto) throws ResourceExistException {
        User user = modelMapper.map(dto, User.class);
        validateUser(user.getUsername(), user.getEmail());
        user.setRole(Role.ROLE_LIBRARIAN.name());
        return getRegisterUserResponse(user);
    }

    public Response findByUserName(String username) throws  ResourceNotFoundException {
        User byUsername = getUserByName(username);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "User found by user name ",
                modelMapper.map(byUsername, StudentUserDto.class));
    }

    public User getUserByName(String username) throws ResourceNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent()) throw new ResourceNotFoundException("user not found : " + username);
        return byUsername.get();
    }

    public Response findAllStudents(Pageable pageable) {
        Page<StudentUserDto> userDtoPage = userRepository.findAllByRole(Role.ROLE_STUDENT.name(), pageable)
                .map(user -> modelMapper.map(user, StudentUserDto.class));
        if (userDtoPage.hasContent()) {
            return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK, "All students", userDtoPage);
        } else {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "No content found", null);
        }
    }

    public Response findAllLibrarian(Pageable pageable) {
        Page<LibrarianDto> userDtoPage = userRepository.findAllByRole(Role.ROLE_LIBRARIAN.name(), pageable)
                .map(user -> modelMapper.map(user, LibrarianDto.class));
        if (userDtoPage.hasContent()) {
            return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK, "All LIBRARIAN", userDtoPage);
        } else {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "No content found", null);
        }
    }

    public Response update(LibrarianDto dto) throws ResourceExistException {
        User validateUser = getUpdateUser(dto.getUsername(), dto.getEmail(), dto.getFistName(), dto.getLastName(),
                                           dto.getMobileNO(), dto.isActive(), dto.isAccountNotLocked());
        User updateUser = userRepository.save(validateUser);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "User updated",
                modelMapper.map(updateUser, LibrarianDto.class));
    }

    public Response update(StudentUserDto dto) throws ResourceExistException {
        User validateUser = getUpdateUser(dto.getUsername(), dto.getEmail(), dto.getFistName(), dto.getLastName(),
                                           dto.getMobileNO(), dto.isActive(), dto.isAccountNotLocked());
        validateUser.setBatch(dto.getBatch());
        validateUser.setDepartment(dto.getDepartment());
        User updateUser = userRepository.save(validateUser);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "User updated",
                modelMapper.map(updateUser, LibrarianDto.class));
    }

    private User getUpdateUser(String username, String email, String fistName, String lastName, String mobileNO,
                               boolean active, boolean accountNotLocked) throws ResourceExistException {
        User validateUser = validateUser(username, email);
        validateUser.setFistName(fistName);
        validateUser.setLastName(lastName);
        validateUser.setMobileNO(mobileNO);
        validateUser.setActive(active);
        validateUser.setAccountNotLocked(accountNotLocked);
        validateUser.setEmail(email);
        return validateUser;
    }
    private Response deleteUser(long id) throws ResourceNotFoundException {
        if (userRepository.existsById(id)){
            bookRequestRepository.deleteAllByUser_Id(id);
            userRepository.deleteById(id);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"User deleted by id : "+id,null);
        }
        throw new ResourceNotFoundException("User not found userId : "+id);
    }
    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private User validateUser(String newUsername, String newEmail) throws ResourceExistException {
        Optional<User> userByEmail = userRepository.findByEmail(newEmail);
        if (userByEmail.isPresent()) throw new ResourceExistException("Email already taken : " + newEmail);
        return userRepository.findByUsername(newUsername)
                .orElseThrow(() -> new ResourceExistException("user name already taken : " + newUsername));
    }

    private Response getRegisterUserResponse(User user) {
        String generatePassword = generatePassword();
        user.setPassword(passwordEncoder.encode(generatePassword));
        user.setAccountNotLocked(true);
        user.setActive(true);
        OnUserPasswordSendEvent onUserPasswordSendEvent = new OnUserPasswordSendEvent(user.getFistName(), user.getEmail(), generatePassword);
        eventPublisher.publishEvent(onUserPasswordSendEvent);
        User saveUser = userRepository.save(user);
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,
                "User created please check your email get the password and log in",
                modelMapper.map(saveUser, StudentUserDto.class));
    }
}
