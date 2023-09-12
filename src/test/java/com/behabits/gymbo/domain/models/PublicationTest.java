package com.behabits.gymbo.domain.models;

import com.behabits.gymbo.domain.exceptions.IncorrectLinkException;
import com.behabits.gymbo.domain.repositories.LinkModelRepository;
import com.behabits.gymbo.domain.repositories.PublicationModelRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PublicationTest {

    private final PublicationModelRepository publicationModelRepository = new PublicationModelRepository();
    private final LinkModelRepository linkModelRepository = new LinkModelRepository();

    @Test
    void givenPublicationWithoutLinksWhenAddLinkThenPublicationHasLink() {
        Publication publication = this.publicationModelRepository.getPublication();
        Link link = this.linkModelRepository.getLinkWithExercise();

        publication.addLink(link);

        assertThat(publication.getLinks(), hasItem(link));
    }

    @Test
    void givenPublicationWithNotSameLinksWhenAddLinkExerciseThenPublicationHasLink() {
        Publication publication = this.publicationModelRepository.getPublicationWithTrainingLink();
        Link link = this.linkModelRepository.getLinkWithExercise();

        publication.addLink(link);

        assertThat(publication.getLinks(), hasItem(link));
    }

    @Test
    void givenPublicationWithSameLinksWhenAddLinkExerciseThenThrowIncorrectLinkException() {
        Publication publication = this.publicationModelRepository.getPublicationWithExerciseLink();
        Link link = this.linkModelRepository.getLinkWithExercise();

        assertThrows(IncorrectLinkException.class, () -> publication.addLink(link));
    }

    @Test
    void givenPublicationWithNotSameLinksWhenAddLinkUserThenPublicationHasLink() {
        Publication publication = this.publicationModelRepository.getPublicationWithExerciseLink();
        Link link = this.linkModelRepository.getLinkWithUser();

        publication.addLink(link);

        assertThat(publication.getLinks(), hasItem(link));
    }

    @Test
    void givenPublicationWithSameLinksWhenAddLinkUserThenThrowIncorrectLinkException() {
        Publication publication = this.publicationModelRepository.getPublicationWithUserLink();
        Link link = this.linkModelRepository.getLinkWithUser();

        assertThrows(IncorrectLinkException.class, () -> publication.addLink(link));
    }

    @Test
    void givenPublicationWithNotSameLinksWhenAddLinkTrainingThenPublicationHasLink() {
        Publication publication = this.publicationModelRepository.getPublicationWithUserLink();
        Link link = this.linkModelRepository.getLinkWithTraining();

        publication.addLink(link);

        assertThat(publication.getLinks(), hasItem(link));
    }

    @Test
    void givenPublicationWithSameLinksWhenAddLinkTrainingThenThrowIncorrectLinkException() {
        Publication publication = this.publicationModelRepository.getPublicationWithTrainingLink();
        Link link = this.linkModelRepository.getLinkWithTraining();

        assertThrows(IncorrectLinkException.class, () -> publication.addLink(link));
    }

}
