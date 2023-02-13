package com.github.voting.service.voting.v1;

import com.github.voting.dto.voting.v1.VotingCreateRequestDto;
import com.github.voting.exception.BusinessException;
import com.github.voting.mapper.voting.v1.VotingMapper;
import com.github.voting.repository.voting.v1.VotingRepository;
import com.github.voting.service.user.v1.UserService;
import com.github.voting.service.votingagenda.v1.VotingAgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotingServiceImpl implements VotingService {

    private static final String ERROR_USER_ALREADY_VOTED = "O usuário já votou nessa pauta!";


    private final VotingRepository votingRepository;
    private final UserService userService;
    private final VotingAgendaService votingAgendaService;
    private final VotingMapper votingMapper;

    @Override
    public void create(VotingCreateRequestDto requestDto) {
        existsVote(requestDto.userDocument, requestDto.votingAgendaId);
        var user = userService.findByDocument(requestDto.getUserDocument());
        var votingAgenda = votingAgendaService.findOpenVotingSessionById(requestDto.getVotingAgendaId());
        votingRepository.save(votingMapper.mapNewVoting(requestDto, votingAgenda, user));
    }

    private void existsVote(String userDocument, Long votingAgendaId){
        if (votingRepository.existsByUserCpfCnpjAndVotingAgendaId(userDocument, votingAgendaId))
            throw new BusinessException(ERROR_USER_ALREADY_VOTED);
    }

}
