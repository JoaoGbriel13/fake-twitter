package com.jg.faketwitter.Service;

import com.jg.faketwitter.Controller.dto.CreateTweetDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface TwitterService {
    public ResponseEntity createTweet(CreateTweetDTO tweetDTO, JwtAuthenticationToken jwt);
    public ResponseEntity deleteTweet(Long tweetId, JwtAuthenticationToken jwt);
    public ResponseEntity feed(int page, int pageSize);
}
