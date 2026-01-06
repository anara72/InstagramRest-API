package peaksoft.instogram.dto.userDto.response;

import lombok.Builder;

@Builder
public record UserResponse(String username,  String email) {
}
