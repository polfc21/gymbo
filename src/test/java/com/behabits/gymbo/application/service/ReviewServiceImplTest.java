package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.ReviewDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.exceptions.SameReviewedException;
import com.behabits.gymbo.domain.exceptions.SameReviewerException;
import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.ReviewModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewDao reviewDao;

    @Mock
    private UserService userService;

    @Mock
    private AuthorityService authorityService;

    private final User reviewer = new UserModelRepository().getReviewer();
    private final User reviewed = new UserModelRepository().getReviewed();
    private final Review review = new ReviewModelRepository().getReview();

    @Test
    void givenReviewWithExistentReviewedWhenCreateReviewThenReturnReview() {
        Review reviewToCreate = mock(Review.class);
        Review reviewCreated = mock(Review.class);
        String usernameReviewed = this.reviewed.getUsername();

        when(this.userService.findUserByUsername(usernameReviewed)).thenReturn(this.reviewed);
        when(this.authorityService.getLoggedUser()).thenReturn(this.reviewer);
        when(this.reviewDao.findReviewByReviewerIdAndReviewedId(this.reviewer.getId(), this.reviewed.getId())).thenReturn(null);
        when(this.reviewDao.saveReview(reviewToCreate)).thenReturn(reviewCreated);

        assertThat(this.reviewService.createReview(reviewToCreate, usernameReviewed), is(reviewCreated));
        verify(reviewToCreate).setReviewer(this.reviewer);
        verify(reviewToCreate).setReviewed(this.reviewed);
        verify(reviewToCreate).setCreatedAt(any());
    }

    @Test
    void givenReviewWithNonExistentReviewedWhenCreateReviewThenThrowUsernameNotFoundException() {
        Review reviewToCreate = mock(Review.class);
        String usernameReviewed = this.reviewed.getUsername();

        when(this.userService.findUserByUsername(usernameReviewed)).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> this.reviewService.createReview(reviewToCreate, usernameReviewed));
    }

    @Test
    void givenReviewWithSameReviewerAndReviewedWhenCreateReviewThenThrowSameReviewerException() {
        Review reviewToCreate = mock(Review.class);
        String usernameReviewed = this.reviewer.getUsername();

        when(this.userService.findUserByUsername(usernameReviewed)).thenReturn(this.reviewer);
        when(this.authorityService.getLoggedUser()).thenReturn(this.reviewer);

        assertThrows(SameReviewerException.class, () -> this.reviewService.createReview(reviewToCreate, usernameReviewed));
    }

    @Test
    void givenReviewWithReviewedAlreadyReviewedByReviewerWhenCreateReviewThenThrowSameReviewedException() {
        Review reviewToCreate = mock(Review.class);
        String usernameReviewed = this.reviewed.getUsername();

        when(this.userService.findUserByUsername(usernameReviewed)).thenReturn(this.reviewed);
        when(this.authorityService.getLoggedUser()).thenReturn(this.reviewer);
        when(this.reviewDao.findReviewByReviewerIdAndReviewedId(this.reviewer.getId(), this.reviewed.getId())).thenReturn(this.review);

        assertThrows(SameReviewedException.class, () -> this.reviewService.createReview(reviewToCreate, usernameReviewed));
    }

    @Test
    void givenExistentReviewIdAndUserHasPermissionsWhenFindReviewByIdThenReturnReview() {
        Long existentId = 1L;

        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.review);
        when(this.reviewDao.findReviewById(existentId)).thenReturn(this.review);

        assertThat(this.reviewService.findReviewById(existentId), is(this.review));
    }

    @Test
    void givenExistentReviewIdAndUserHasNotPermissionsWhenFindReviewByIdThenThrowPermissionsException() {
        Long nonExistentId = 1L;

        when(this.reviewDao.findReviewById(nonExistentId)).thenReturn(this.review);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.review);

        assertThrows(PermissionsException.class, () -> this.reviewService.findReviewById(nonExistentId));
    }

    @Test
    void givenNonExistentReviewIdWhenFindReviewByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.reviewDao.findReviewById(nonExistentId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.reviewService.findReviewById(nonExistentId));
    }

    @Test
    void givenExistentUsernameWhenFindAllReviewsByUsernameThenReturnReviews() {
        String existentUsername = this.reviewed.getUsername();

        when(this.userService.findUserByUsername(existentUsername)).thenReturn(this.reviewed);
        when(this.reviewDao.findAllReviewsByReviewedId(this.reviewed.getId())).thenReturn(List.of(this.review));

        assertThat(this.reviewService.findAllReviewsByUsername(existentUsername), is(List.of(this.review)));
    }

    @Test
    void givenNonExistentUsernameWhenFindAllReviewsByUsernameThenThrowUsernameNotFoundException() {
        String nonExistentUsername = this.reviewed.getUsername();

        when(this.userService.findUserByUsername(nonExistentUsername)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.reviewService.findAllReviewsByUsername(nonExistentUsername));
    }

    @Test
    void givenExistentReviewWhenUpdateReviewThenReturnReview() {
        Long existentId = 1L;
        Review reviewToUpdate = mock(Review.class);

        when(this.reviewDao.findReviewById(existentId)).thenReturn(reviewToUpdate);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(reviewToUpdate);
        when(this.reviewDao.saveReview(reviewToUpdate)).thenReturn(reviewToUpdate);

        assertThat(this.reviewService.updateReview(existentId, this.review), is(reviewToUpdate));
        verify(reviewToUpdate).setRating(this.review.getRating());
        verify(reviewToUpdate).setComment(this.review.getComment());
        verify(reviewToUpdate).setUpdatedAt(any());
    }

    @Test
    void givenNonExistentReviewWhenUpdateReviewThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.reviewDao.findReviewById(nonExistentId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.reviewService.updateReview(nonExistentId, this.review));
    }

    @Test
    void givenExistentReviewWithoutPermissionsWhenUpdateReviewThenThrowPermissionsException() {
        Long existentId = 1L;
        Review reviewToUpdate = mock(Review.class);

        when(this.reviewDao.findReviewById(existentId)).thenReturn(reviewToUpdate);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(reviewToUpdate);

        assertThrows(PermissionsException.class, () -> this.reviewService.updateReview(existentId, this.review));
    }

    @Test
    void givenExistentReviewIdWhenDeleteReviewThenDeleteReview() {
        Long existentId = 1L;
        Review reviewToDelete = mock(Review.class);

        when(this.reviewDao.findReviewById(existentId)).thenReturn(reviewToDelete);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(reviewToDelete);
        doNothing().when(this.reviewDao).deleteReview(reviewToDelete);

        try {
            this.reviewService.deleteReview(existentId);
        } catch (Exception e) {
            assertThat(e, is(nullValue()));
        }
    }

    @Test
    void givenNonExistentReviewIdWhenDeleteReviewThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.reviewDao.findReviewById(nonExistentId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.reviewService.deleteReview(nonExistentId));
    }

    @Test
    void givenExistentReviewWithoutPermissionsWhenDeleteReviewThenThrowPermissionsException() {
        Long existentId = 1L;
        Review reviewToDelete = mock(Review.class);

        when(this.reviewDao.findReviewById(existentId)).thenReturn(reviewToDelete);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(reviewToDelete);

        assertThrows(PermissionsException.class, () -> this.reviewService.deleteReview(existentId));
    }

}
