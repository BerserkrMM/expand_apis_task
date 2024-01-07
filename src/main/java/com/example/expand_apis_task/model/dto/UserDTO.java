package com.example.expand_apis_task.model.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String password;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;

        if (getId() != null ? !getId().equals(userDTO.getId()) : userDTO.getId() != null) return false;
        if (getUsername() != null ? !getUsername().equals(userDTO.getUsername()) : userDTO.getUsername() != null)
            return false;
        return getPassword() != null ? getPassword().equals(userDTO.getPassword()) : userDTO.getPassword() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

