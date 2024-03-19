package store.mybooks.resource.review.controller;

import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import store.mybooks.resource.config.HeaderProperties;
import store.mybooks.resource.error.Utils;
import store.mybooks.resource.review.dto.reqeust.ReviewCreateRequest;
import store.mybooks.resource.review.dto.reqeust.ReviewModifyRequest;
import store.mybooks.resource.review.dto.response.ReviewCreateResponse;
import store.mybooks.resource.review.dto.response.ReviewDetailGetResponse;
import store.mybooks.resource.review.dto.response.ReviewGetResponse;
import store.mybooks.resource.review.dto.response.ReviewModifyResponse;
import store.mybooks.resource.review.service.ReviewService;

/**
 * packageName    : store.mybooks.resource.review.controller<br>
 * fileName       : ReviewController<br>
 * author         : masiljangajji<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        masiljangajji       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewModifyResponse> modifyReview(@PathVariable(name = "reviewId") Long reviewId
            , @Valid @RequestBody ReviewModifyRequest modifyRequest, BindingResult bindingResult
            , @RequestHeader(HeaderProperties.USER_ID) Long userId) {

        Utils.validateRequest(bindingResult);
        return new ResponseEntity<>(reviewService.modifyReview(userId, reviewId, modifyRequest), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReviewCreateResponse> createReview(@RequestHeader(HeaderProperties.USER_ID) Long userId,
                                                             @Valid @RequestPart("request")
                                                             ReviewCreateRequest createRequest,
                                                             BindingResult bindingResult,
                                                             @RequestPart(value = "contentImage",required = false) MultipartFile image)
            throws IOException {

        Utils.validateRequest(bindingResult);
        return new ResponseEntity<>(reviewService.createReview(createRequest, userId, image), HttpStatus.CREATED);
    }


    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewGetResponse> findReview(@RequestHeader(HeaderProperties.USER_ID) Long userId,
                                                        @PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.findReview(userId, reviewId), HttpStatus.OK);
    }

    // 내가 쓴 모든 리뷰
    @GetMapping
    public ResponseEntity<Page<ReviewGetResponse>> findReviewByUserId(
            @RequestHeader(HeaderProperties.USER_ID) Long userId,
            Pageable pageable) {

        return new ResponseEntity<>(reviewService.findReviewByUserId(userId, pageable), HttpStatus.OK);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Page<ReviewDetailGetResponse>> findReviewByBookId(@PathVariable(name = "bookId") Long bookId,
                                                                            Pageable pageable) {
        return new ResponseEntity<>(reviewService.findReviewByBookId(bookId, pageable), HttpStatus.OK);
    }


}
