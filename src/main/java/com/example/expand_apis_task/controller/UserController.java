package com.example.expand_apis_task.controller;

import com.example.expand_apis_task.model.dto.base.ResponseDTO;
import com.example.expand_apis_task.model.dto.base.ResponseDTOFactory;
import com.example.expand_apis_task.model.dto.UserDTO;
import com.example.expand_apis_task.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO<String>> addUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return ResponseEntity.ok(ResponseDTOFactory.getOkResponseDto("User added successfully"));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO<String>> authenticateUser(@RequestBody UserDTO userDTO) {
        String token = userService.authenticateUser(userDTO);
        return ResponseEntity.ok(ResponseDTOFactory.getOkResponseDto(token));
    }
}
