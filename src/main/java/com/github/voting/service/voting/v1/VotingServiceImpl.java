package com.github.voting.service.voting.v1;

import com.github.voting.domain.voting.Voting;
import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.voting.v1.VotingCreateRequestDto;
import com.github.voting.exception.BusinessException;
import com.github.voting.exception.NotFoundException;
import com.github.voting.mapper.voting.v1.VotingMapper;
import com.github.voting.repository.voting.v1.VotingRepository;
import com.github.voting.repository.votingagenda.v1.VotingAgendaRepository;
import com.github.voting.service.user.v1.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class VotingServiceImpl implements VotingService {

    private static final String ERROR_USER_ALREADY_VOTED = "O usuário já votou nessa pauta!";
    private static final String ERROR_VOTING_AGENDA_NOT_FOUND = "VotingAgenda não encontrada!";

    private final VotingRepository votingRepository;
    private final VotingAgendaRepository votingAgendaRepository;
    private final UserService userService;
    private final VotingMapper votingMapper;


    @Override
    public void create(VotingCreateRequestDto requestDto) {
        var currentDate = LocalDateTime.now();
        existsVote(requestDto.userDocument, requestDto.votingAgendaId);
        var votingAgenda = votingAgendaRepository.findByIdAndStartVoteBeforeAndFinalizeVoteAfter(requestDto.votingAgendaId, currentDate, currentDate )
                .orElseThrow(() -> new NotFoundException(ERROR_VOTING_AGENDA_NOT_FOUND));
        var user = userService.findByDocument(requestDto.getUserDocument());
        votingRepository.save(votingMapper.mapNewVoting(requestDto, votingAgenda, user));
    }

    @Override
    public boolean isApproved(VotingAgenda votingAgenda){
        List<Voting> votings = votingRepository.findAllByVotingAgenda(votingAgenda);
        var resultVotings = votings.stream().collect(Collectors.groupingBy(Voting::getWasApproved, Collectors.counting()));
        return  resultVotings.get(TRUE) > resultVotings.get(FALSE);
    }

    private void existsVote(String userDocument, Long votingAgendaId){
        if (votingRepository.existsByUserCpfCnpjAndVotingAgendaId(userDocument, votingAgendaId))
            throw new BusinessException(ERROR_USER_ALREADY_VOTED);
    }




}
