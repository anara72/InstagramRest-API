package peaksoft.instogram.dto.userInfoDto.response;

import lombok.Builder;
import peaksoft.instogram.enums.Gender;
import tools.jackson.databind.deser.bean.CreatorCandidate;
@Builder
public record UserInfoResponse(String fullName,
        String biography,
        Gender gender,
        String image) {

}
