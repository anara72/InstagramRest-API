package peaksoft.instogram.dto.userDto.request;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import peaksoft.instogram.enums.Role;

public record UserRequest(String userName, String password, String email, Role role, String phoneNumber)
{
}