package com.jg.faketwitter.Controller;

import com.jg.faketwitter.Controller.dto.CreateTweetDTO;
import com.jg.faketwitter.Service.TwitterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class TwitterController {
    private final TwitterService twitterService;

    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("/feed")
    public ResponseEntity feed(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return twitterService.feed(page,pageSize);
    }
    @PostMapping("/tweet")
    public ResponseEntity createTweet(@RequestBody CreateTweetDTO dto, JwtAuthenticationToken jwt){
        return twitterService.createTweet(dto, jwt);
    }

    @DeleteMapping("/tweet/{id}")
    public ResponseEntity deleteTweet(@PathVariable("id") Long tweetId, JwtAuthenticationToken token){
        return twitterService.deleteTweet(tweetId, token);
    }

}
