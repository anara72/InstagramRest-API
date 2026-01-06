package peaksoft.instogram.dto.postDto.request;

import java.util.List;

public record PostRequest(String title, String description, String imagUrl, List<Long> collabsUserId) {
}
