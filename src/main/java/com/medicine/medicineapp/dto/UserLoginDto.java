package com.medicine.medicineapp.dto;

public class UserLoginDto {
    
    private String userId;

    private String password;

    private UserTypeDto userTypeDto;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTypeDto getUserTypeDto() {
        return userTypeDto;
    }

    public void setUserTypeDto(UserTypeDto userType) {
        this.userTypeDto = userType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((userTypeDto == null) ? 0 : userTypeDto.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserLoginDto other = (UserLoginDto) obj;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (userTypeDto.name().compareTo(other.userTypeDto.name()) != 0)
            return false;
        return true;
    }

    
}
