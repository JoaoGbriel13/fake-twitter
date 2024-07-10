package com.jg.faketwitter.Service.ServiceImpl;

import com.jg.faketwitter.Controller.dto.CreateTweetDTO;
import com.jg.faketwitter.Controller.dto.FeedDto;
import com.jg.faketwitter.Controller.dto.FeedItemDto;
import com.jg.faketwitter.Model.Role;
import com.jg.faketwitter.Model.Tweet;
import com.jg.faketwitter.Repositories.RoleRepository;
import com.jg.faketwitter.Repositories.TweetRepository;
import com.jg.faketwitter.Repositories.UserRepository;
import com.jg.faketwitter.Service.TwitterService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;
@Service
public class TwitterServiceImpl implements TwitterService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public TwitterServiceImpl(TweetRepository tweetRepository, UserRepository userRepository,
                              RoleRepository roleRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity createTweet(CreateTweetDTO tweetDTO, JwtAuthenticationToken jwt) {
        var user = userRepository.findById(UUID.fromString(jwt.getName()));
        var tweet = new Tweet();
        tweet.setContent(tweetDTO.content());
        tweet.setUser(user.get());
        tweetRepository.save(tweet);
        return ResponseEntity.ok().body("Tweet criado");
    }

    @Override
    public ResponseEntity deleteTweet(Long tweetId, JwtAuthenticationToken jwt) {
        var tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var userCheck = userRepository.findById(UUID.fromString(jwt.getName()));
        var roleAdmin = roleRepository.findByName("admin");

        if (tweet.getUser().getId().equals(UUID.fromString(jwt.getName())) ||
       userCheck.get().getRoles().equals(Set.of(roleAdmin))){
            tweetRepository.deleteById(tweetId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Tweet removido");
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ação não permitida");
        }

    }

    @Override
    public ResponseEntity<FeedDto> feed(int page, int pageSize) {
        var tweets = tweetRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimeStamp")
        ).map(tweet -> new FeedItemDto(tweet.getId(), tweet.getContent(), tweet.getUser().getUsername()));
        return ResponseEntity.ok(new FeedDto(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));
    }
}
