package com.github.voting.service.votingagenda.v1;

import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.PageDto;
import com.github.voting.dto.PaginationDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaStartRequestDto;
import com.github.voting.exception.NotFoundException;
import com.github.voting.mapper.votingagenda.v1.VotingAgendaMapper;
import com.github.voting.repository.votingagenda.v1.VotingAgendaRepository;
import com.github.voting.service.user.v1.UserService;
import com.github.voting.service.voting.v1.VotingService;
import com.github.voting.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class VotingAgendaServiceImpl implements VotingAgendaService {

    private static final String ERROR_VOTING_AGENDA_NOT_FOUND = "Pauta de votação não encontrada!";
    private static final Boolean WAS_NOT_COUNTED = FALSE;

    private final VotingAgendaRepository votingAgendaRepository;
    private final VotingAgendaMapper votingAgendaMapper;
    private final UserService userService;
    private final VotingService votingService;

    @Override
    public void create(VotingAgendaCreateRequestDto requestDto) {
        var user = userService.findByDocument(requestDto.getUserDocument());
        votingAgendaRepository.save(votingAgendaMapper.mapNewVotingAgenda(requestDto, user));
    }

    @Override
    @Transactional
    public PaginationDto<VotingAgendaResponseDto> findVotingSessions(String cpfCnpj, PageDto pageDto){
        countVotes();
        var user = userService.findByDocument(cpfCnpj);
        Page<VotingAgenda> votingAgendaPage = votingAgendaRepository.findAllByUser(user, PaginationUtil.toPageableWithSort(pageDto));
        var votingAgendaDtoList = votingAgendaMapper.mapVotingAgendaResponseDtoList(votingAgendaPage.getContent());
        return PaginationUtil.toPaginationDtoWithContentMapping(votingAgendaPage, votingAgendaDtoList);
    }

    @Override
    public void startVotingSession(Long id, VotingAgendaStartRequestDto requestDto){
        var votingAgenda = votingAgendaRepository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_VOTING_AGENDA_NOT_FOUND));
        votingAgendaMapper.mapVotingTime(votingAgenda, requestDto);
        votingAgendaRepository.save(votingAgenda);
    }

    @Override
    public PaginationDto<VotingAgendaResponseDto> findOpenVotingSession(PageDto pageDto){
        var currentDate = LocalDateTime.now();
        Page<VotingAgenda> votingAgendaPage = votingAgendaRepository.findAllByStartVoteBeforeAndFinalizeVoteAfter(currentDate, currentDate, PaginationUtil.toPageableWithSort(pageDto));

        var votingAgendaDtoList = votingAgendaMapper.mapVotingAgendaResponseDtoList(votingAgendaPage.getContent());

        return PaginationUtil.toPaginationDtoWithContentMapping(votingAgendaPage, votingAgendaDtoList);
    }

    @Override
    @Transactional
    public PaginationDto<VotingAgendaResponseDto> findSessionEnd(PageDto pageDto){
        var currentDate = LocalDateTime.now();
        countVotes();
        Page<VotingAgenda> votingAgendaPage = votingAgendaRepository.findAllByFinalizeVoteBefore(currentDate, PaginationUtil.toPageableWithSort(pageDto));
        var votingAgendaDtoList = votingAgendaMapper.mapVotingAgendaResponseDtoList(votingAgendaPage.getContent());
        return PaginationUtil.toPaginationDtoWithContentMapping(votingAgendaPage, votingAgendaDtoList);
    }

    private void countVotes(){
        var currentDate = LocalDateTime.now();
        var votingAgendaList = votingAgendaRepository.findAllByFinalizeVoteBeforeAndWasCounted(currentDate, WAS_NOT_COUNTED);
        votingAgendaList.forEach(agenda ->
        {
            agenda.setWasCounted(TRUE);
            agenda.setWasApproved(votingService.isApproved(agenda));

        });
        votingAgendaRepository.saveAll(votingAgendaList);
    }

}
