package peaksoft.instogram.dto.userDto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record UserUpdateResponse(HttpStatus status, String message, String token) {
}
