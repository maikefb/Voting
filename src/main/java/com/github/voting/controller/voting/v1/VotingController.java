package com.github.voting.controller.voting.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/voting")
public class VotingController implements VotingApi {

    @Override
    @PostMapping
    public void vote() {

    }
}
