package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.feedback.FeedbackRequest;
import peaksoft.dto.feedback.FeedbackResponse;
import peaksoft.models.Feedback;
import peaksoft.models.User;
import peaksoft.repositories.FeedbackRepository;
import peaksoft.repositories.UserRepository;
import peaksoft.service.FeedbackService;
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    @Override
    public FeedbackResponse saveFeedback(FeedbackRequest feedbackRequest) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new RuntimeException("User with email:" + email + " not found"));
        Feedback feedback = feedbackRequest.build();
        user.addFeedback(feedback);
        feedbackRepository.save(feedback);
        return new FeedbackResponse(
                feedback.getId(),
                feedback.getDescription(),
                feedback.getImage(),
                user.getId(),
                user.getEmail()

        );
    }
}
