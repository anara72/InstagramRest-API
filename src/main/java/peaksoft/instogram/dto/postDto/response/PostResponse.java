package peaksoft.instogram.dto.postDto.response;

import lombok.Builder;
import peaksoft.instogram.entity.User;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PostResponse(Long id, String title, String description,
                           String username, List<String> collabUsers, String collabs, String imagUrl,
                           LocalDate createdAt) {
}
