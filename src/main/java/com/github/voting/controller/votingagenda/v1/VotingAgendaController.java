package com.github.voting.controller.votingagenda.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/voting-agenda")
public class VotingAgendaController implements VotingAgendaApi {

    @Override
    @PostMapping
    public void create() {

    }

    @Override
    @PutMapping
    public void votingSession() {

    }

    @Override
    @GetMapping("/session-end")
    public void getSessionEnd() {

    }

    @Override
    @GetMapping
    public void getOpenVotingSession() {

    }
}
