package com.coffeeandsoftware.api.services;

import com.coffeeandsoftware.api.dto.ReturnDTO.ActivityDTO;
import com.coffeeandsoftware.api.dto.ReturnDTO.ActivityType;
import com.coffeeandsoftware.api.model.Comment;
import com.coffeeandsoftware.api.model.Publication;
import com.coffeeandsoftware.api.model.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    UserService userService;

    @Autowired
    PublicationService publicationService;

    public List<ActivityDTO> getActivitiesFromUser(String userId) {
        List<ActivityDTO> activityDTOList = new ArrayList<>();

        List<Publication> publications = publicationService.getAllUserPublications(userId);
        for (Publication publication: publications) {
            for (Reaction reaction: publication.getReactions()) {
                activityDTOList.add(
                        new ActivityDTO(
                                reaction.getAuthor().getU_name(),
                                reaction.getAuthor().getPhotoURL(),
                                ActivityType.LIKE,
                                reaction.getReactionDate(),
                                ""
                        )
                );
            }
            for (Comment comment: publication.getComments()) {
                activityDTOList.add(
                        new ActivityDTO(
                                comment.getAuthor().getU_name(),
                                comment.getAuthor().getPhotoURL(),
                                ActivityType.COMMENT,
                                comment.getCreation_date(),
                                comment.getC_text()
                        )
                );
            }
        }

        return activityDTOList;
    }
}
