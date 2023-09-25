package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.feedback.FeedbackRequest;
import peaksoft.dto.feedback.FeedbackResponse;
import peaksoft.service.FeedbackService;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackApi {
    private final FeedbackService feedbackService;
    @PostMapping
    @Secured("USER")
    public ResponseEntity<FeedbackResponse> saveFeedback(@RequestBody FeedbackRequest feedbackRequest){
        return ResponseEntity.ok(feedbackService.saveFeedback(feedbackRequest));
    }

}
