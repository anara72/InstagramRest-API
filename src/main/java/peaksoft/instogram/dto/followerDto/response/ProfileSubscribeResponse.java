package peaksoft.instogram.dto.followerDto.response;

import lombok.Builder;

@Builder
public record ProfileSubscribeResponse(Long targetUserId, boolean sub, String btnText) {
}