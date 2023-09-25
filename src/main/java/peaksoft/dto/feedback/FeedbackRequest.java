package peaksoft.dto.feedback;

import peaksoft.models.Feedback;

public record FeedbackRequest(
        String description,
        String image
) {
    public Feedback build(){
        Feedback feedback = new Feedback();
        feedback.setDescription(this.description);
        feedback.setImage(this.image);
        return  feedback;
    }
}
