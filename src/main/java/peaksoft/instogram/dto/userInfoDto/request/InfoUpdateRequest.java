package peaksoft.instogram.dto.userInfoDto.request;

import peaksoft.instogram.enums.Gender;

public record InfoUpdateRequest (String fullName, String biography, Gender gender){
}
