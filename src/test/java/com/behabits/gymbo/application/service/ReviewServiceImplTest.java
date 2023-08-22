package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.ReviewDao;
import com.behabits.gymbo.domain.exceptions.SameReviewerException;
import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

    @Test
    void givenReviewWithExistentReviewedWhenCreateReviewThenReturnReview() {
        Review reviewToCreate = mock(Review.class);
        Review reviewCreated = mock(Review.class);
        String usernameReviewed = this.reviewed.getUsername();

        when(this.userService.findUserByUsername(usernameReviewed)).thenReturn(this.reviewed);
        when(this.authorityService.getLoggedUser()).thenReturn(this.reviewer);
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

}
