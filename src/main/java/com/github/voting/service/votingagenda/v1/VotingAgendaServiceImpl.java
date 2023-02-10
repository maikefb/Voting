package com.github.voting.service.votingagenda.v1;

import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.PageDto;
import com.github.voting.dto.PaginationDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import com.github.voting.mapper.votingagenda.v1.VotingAgendaMapper;
import com.github.voting.repository.votingagenda.v1.VotingAgendaRepository;
import com.github.voting.service.user.v1.UserService;
import com.github.voting.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotingAgendaServiceImpl implements VotingAgendaService {

    private final VotingAgendaRepository votingAgendaRepository;
    private final VotingAgendaMapper votingAgendaMapper;
    private final UserService userService;

    @Override
    public void create(VotingAgendaCreateRequestDto requestDto) {
        var user = userService.findByDocument(requestDto.getUserDocument());
        votingAgendaRepository.save(votingAgendaMapper.mapNewVotingAgenda(requestDto, user));
    }

    public PaginationDto<VotingAgendaResponseDto> findVotingSessions(String cpfCnpj, PageDto pageDto){
        var user = userService.findByDocument(cpfCnpj);
        Page<VotingAgenda> votingAgendaPage = votingAgendaRepository.findAllByUser(user, PaginationUtil.toPageableWithSort(pageDto));
        var votingAgendaDtoList = votingAgendaMapper.mapVotingAgendaResponseDtoList(votingAgendaPage.getContent());

        return PaginationUtil.toPaginationDtoWithContentMapping(votingAgendaPage, votingAgendaDtoList);
    }

}
