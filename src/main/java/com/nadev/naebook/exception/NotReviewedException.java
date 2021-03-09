package com.nadev.naebook.exception;

public class NotReviewedException extends RuntimeException {
    private static final String MESSAGE = "아직 리뷰가 되지 않았습니다.";

    public NotReviewedException() {
        super(MESSAGE);
    }
}
