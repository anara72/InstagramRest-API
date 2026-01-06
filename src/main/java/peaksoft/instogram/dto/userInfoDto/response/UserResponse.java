package peaksoft.instogram.dto.userInfoDto.response;

import peaksoft.instogram.dto.postDto.response.PostResponse;

import java.util.List;

public record UserResponse(String username, String fullName, String image, int subscribers, int subscriptions, List<PostResponse> posts) {
}
