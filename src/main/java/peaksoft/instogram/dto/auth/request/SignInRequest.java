package peaksoft.instogram.dto.auth.request;

import peaksoft.instogram.enums.Role;

public record SignInRequest(String userName, String email,
                            String password, String phoneNumber, Role role) {
}
