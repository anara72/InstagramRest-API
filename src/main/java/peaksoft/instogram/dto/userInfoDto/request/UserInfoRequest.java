package peaksoft.instogram.dto.userInfoDto.request;

import lombok.Data;
import peaksoft.instogram.enums.Gender;

public record UserInfoRequest(String fullName,
                              String biography,
                              Gender gender,
                              String image)
{
}
