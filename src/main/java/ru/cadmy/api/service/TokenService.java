package ru.cadmy.api.service;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.extern.slf4j.Slf4j;
import ru.cadmy.api.model.Token;

/**
 * Created by Cadmy on 08.07.2017.
 */
@Service
@Slf4j
public class TokenService {

    ProfileService profileService;
    List<Token> tokens;

    @Autowired
    public TokenService(ProfileService profileService) {
        Assert.notNull(profileService, "ProfileService cannot be null!");
        this.profileService = profileService;
        tokens = Lists.newArrayList(
                new Token("1725", LocalDateTime.now(), LocalDateTime.now().plusDays(7L), profileService.getProfileById(1)),
                new Token("1796", LocalDateTime.now(), LocalDateTime.now().plusDays(7L), profileService.getProfileById(2)),
                new Token("1894", LocalDateTime.now(), LocalDateTime.now().plusDays(7L), profileService.getProfileById(3)));
    }

    public Token getToken(String tokenString) {
        Token result = null;
        try {
            result = tokens.stream().filter(token -> token.getToken().equals(tokenString))
                    .reduce((a, b) -> {
                        throw new IllegalStateException("Multiple elements with same id");
                    }).get();
        } catch (IllegalStateException e) {
            log.error("Multiple elements with same token");
        } catch (NoSuchElementException e) {
            log.error("No such element");
        }
        return result;
    }
}
