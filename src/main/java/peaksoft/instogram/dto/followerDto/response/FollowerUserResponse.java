package peaksoft.instogram.dto.followerDto.response;

import lombok.Builder;

@Builder
public record FollowerUserResponse(String username, String image) {
}



