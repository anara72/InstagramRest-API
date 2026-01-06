package peaksoft.instogram.dto.userDto.response;

import lombok.Builder;
import peaksoft.instogram.dto.postDto.response.PostResponse;

import java.util.List;

@Builder
public record UserProfileResponse(String username, String fullName, String image, int subscribers, int subscriptions, List<PostResponse> posts) {
}
