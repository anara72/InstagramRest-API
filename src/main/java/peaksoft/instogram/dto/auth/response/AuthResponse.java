package peaksoft.instogram.dto.auth.response;

import lombok.Builder;
import peaksoft.instogram.enums.Role;

@Builder
    public record AuthResponse(
            Long id,
            String token,
            Role role
)
{

}

