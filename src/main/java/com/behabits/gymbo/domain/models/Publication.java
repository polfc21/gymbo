package com.behabits.gymbo.domain.models;

import com.behabits.gymbo.domain.exceptions.IncorrectLinkException;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publication {
    private Long id;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User postedBy;
    private List<File> files;
    private List<Link> links;
    private Sport sport;

    public void addLink(Link link) {
        if (this.links == null) {
            this.links = List.of(link);
        } else {
            this.checkLinksHasNotSameLink(link, this.links);
            List<Link> newLinks = new ArrayList<>(this.links);
            newLinks.add(link);
            this.links = List.copyOf(newLinks);
        }
    }

    private void checkLinksHasNotSameLink(Link linkToAdd, List<Link> links) {
        for (Link link : links) {
            switch (linkToAdd.getEntity()) {
                case "EXERCISE" -> this.checkExerciseLinksAreNotSame(linkToAdd, link);
                case "USER" -> this.checkLinksHasNotSameUser(linkToAdd, link);
                case "TRAINING" -> this.checkLinksHasNotSameTraining(linkToAdd, link);
            }
        }
    }

    private void checkExerciseLinksAreNotSame(Link linkToAdd, Link link) {
        if (link.getEntity().equals("EXERCISE")) {
            if (link.getExercise().getId().equals(linkToAdd.getExercise().getId())) {
                throw new IncorrectLinkException("Exercise link already exists");
            }
        }
    }

    private void checkLinksHasNotSameUser(Link linkToAdd, Link link) {
        if (link.getEntity().equals("USER")) {
            if (link.getUser().getUsername().equals(linkToAdd.getUser().getUsername())) {
                throw new IncorrectLinkException("User link already exists");
            }
        }
    }

    private void checkLinksHasNotSameTraining(Link linkToAdd, Link link) {
        if (link.getEntity().equals("TRAINING")) {
            if (link.getTraining().getId().equals(linkToAdd.getTraining().getId())) {
                throw new IncorrectLinkException("Training link already exists");
            }
        }
    }

}
