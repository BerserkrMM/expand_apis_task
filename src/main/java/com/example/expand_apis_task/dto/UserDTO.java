package com.example.expand_apis_task.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String password;

    private UserDTO() {
        // Private constructor to prevent direct instantiation
    }

    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    public static class UserDtoBuilder {
        private final UserDTO userDTO;

        private UserDtoBuilder() {
            userDTO = new UserDTO();
        }

        public UserDtoBuilder id(Long id) {
            userDTO.id = id;
            return this;
        }

        public UserDtoBuilder username(String username) {
            userDTO.username = username;
            return this;
        }

        public UserDtoBuilder password(String password) {
            userDTO.password = password;
            return this;
        }

        public UserDTO build() {
            return userDTO;
        }
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

