package peaksoft.service;

import peaksoft.dto.feedback.FeedbackRequest;
import peaksoft.dto.feedback.FeedbackResponse;

public interface FeedbackService {
    FeedbackResponse saveFeedback(FeedbackRequest feedbackRequest);
}
