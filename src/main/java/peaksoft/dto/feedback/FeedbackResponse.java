package peaksoft.dto.feedback;

import peaksoft.models.Feedback;

public record FeedbackResponse(
    Long feedbackId,
    String description,
    String image,
    Long userId,
    String email
) {
}
