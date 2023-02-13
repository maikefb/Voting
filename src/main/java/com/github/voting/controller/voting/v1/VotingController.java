package com.github.voting.controller.voting.v1;

import com.github.voting.dto.voting.v1.VotingCreateRequestDto;
import com.github.voting.service.voting.v1.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/voting")
public class VotingController implements VotingApi {

    private final VotingService votingService;

    @Override
    @PostMapping
    public void vote(@RequestBody @Valid VotingCreateRequestDto requestDto) {
        votingService.create(requestDto);
    }
}
