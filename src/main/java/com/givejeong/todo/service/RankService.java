package com.givejeong.todo.service;

import com.givejeong.todo.dto.board.RankDto;
import com.givejeong.todo.dto.board.RankFormDto;
import com.givejeong.todo.repository.ProgrammingRoleRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.givejeong.todo.domain.QProgrammingRole.programmingRole;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankService{
    private final ProgrammingRoleRepository programmingRoleRepository;

    public RankFormDto top5Rank() {
        long qnaTotalCount = programmingRoleRepository.qnaTotalCount();
        List<Tuple> qnaRank = programmingRoleRepository.qnaTop5Rank();

        List<RankDto> qnaRankCollect = qnaRank.stream().map(o -> new RankDto(o.get(programmingRole.programmingEnum)
                .name()
                .toLowerCase(Locale.ROOT), (double) o.get(programmingRole.count()) / (double) qnaTotalCount * 100.0))
                .collect(Collectors.toList());


        long studyTotalCount = programmingRoleRepository.studyTotalCount();
        List<Tuple> studyRank = programmingRoleRepository.studyTop5Rank();

        List<RankDto> studyRankCollect = studyRank.stream().map(o -> new RankDto(o.get(programmingRole.programmingEnum)
                .name()
                .toLowerCase(Locale.ROOT), (double) o.get(programmingRole.count()) / (double) studyTotalCount * 100.0))
                .collect(Collectors.toList());

        long totalCount = programmingRoleRepository.totalCount();
        List<Tuple> totalRank = programmingRoleRepository.Top5Rank();

        List<RankDto> totalRankCollect = totalRank.stream().map(o -> new RankDto(o.get(programmingRole.programmingEnum)
                .name()
                .toLowerCase(Locale.ROOT), (double) o.get(programmingRole.count()) / (double) totalCount * 100.0))
                .collect(Collectors.toList());


        return new RankFormDto(qnaRankCollect,studyRankCollect,totalRankCollect);

    }
}
