package com.agenda.vote.agenda.interfaces;


import com.agenda.vote.agenda.facade.AgendaFacade;
import com.agenda.vote.agenda.interfaces.request.*;
import com.agenda.vote.agenda.interfaces.response.AgendaResponse;
import com.agenda.vote.common.annotation.Admin;
import com.agenda.vote.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/agendas")
@RequiredArgsConstructor
public class AgendaApiController {

    private final AgendaFacade agendaFacade;


    /**
     * 안건 생성 API
     * [POST] /api/v1/agendas
     */
    @Admin
    @PostMapping
    public CommonResponse<HttpStatus> createAgenda(@Validated @RequestBody AgendaCreateRequest agendaCreateRequest,
                                                   HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.createAgenda(userId, agendaCreateRequest);
        return CommonResponse.success(HttpStatus.CREATED);
    }

    /**
     * 안건 목록 조회 API
     * [GET] /api/v1/agendas
     */
    @GetMapping
    public CommonResponse<List<AgendaResponse>> getAgendaResponseList(HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        return CommonResponse.success(agendaFacade.getAgendaResponseList(userId));
    }

    /**
     * 안건 조회 API
     * [GET] /api/v1/agendas/{agendaId}
     */
    @GetMapping("/{agendaId}")
    public CommonResponse<AgendaResponse> getAgendaResponse(@PathVariable Long agendaId,
                                                            HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        return CommonResponse.success(agendaFacade.getAgendaResponse(userId, agendaId));
    }

    /**
     * 안건 수정 API
     * [PATCH] /api/v1/agendas/{agendaId}
     */
    @Admin
    @PatchMapping("/{agendaId}")
    public CommonResponse<HttpStatus> updateAgenda(@PathVariable Long agendaId,
                                                       @Validated @RequestBody AgendaUpdateRequest agendaUpdateRequest,
                                                       HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.updateAgenda(userId, agendaId, agendaUpdateRequest);
        return CommonResponse.success(HttpStatus.OK);
    }

    /**
     * 안건 삭제 API
     * [DELETE] /api/v1/agendas/{agendaId}
     */
    @Admin
    @DeleteMapping("/{agendaId}")
    public CommonResponse<HttpStatus> deleteAgenda(@PathVariable Long agendaId,
                                                   HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.deleteAgenda(userId, agendaId);
        return CommonResponse.success(HttpStatus.OK);
    }


    /**
     * 투표 생성 API
     * [POST] /api/v1/agendas/{agendaId}/votes
     */
    @Admin
    @PostMapping("/{agendaId}/votes")
    public CommonResponse<HttpStatus> createVote(@PathVariable Long agendaId,
                                                 @Validated @RequestBody VoteCreateRequest voteCreateRequest,
                                                 HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.createVote(userId, agendaId, voteCreateRequest);
        return CommonResponse.success(HttpStatus.CREATED);
    }

    /**
     * 투표 수행 API
     * [PATCH] /api/v1/agendas/{agendaId}/votes
     */
    @PatchMapping("/{agendaId}/votes")
    public CommonResponse<HttpStatus> voting(@PathVariable Long agendaId,
                                             @RequestBody VotingRequest votingRequest,
                                             HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.voting(userId, agendaId, votingRequest);
        return CommonResponse.success(HttpStatus.OK);
    }

    /**
     * 안건 투표 목록 검색 API
     * [GET] /api/v1/agendas/votes/search?userId=&startDate=&endDate=
     */
    @GetMapping("/votes/search")
    public CommonResponse<List<AgendaResponse>> searchAgendaResponses(@RequestParam(required = false) Long searchUserId,
                                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
                                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate,
                                                                      HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        VoteSearchFilter voteSearchFilter = new VoteSearchFilter(searchUserId, startDate, endDate);
        return CommonResponse.success(agendaFacade.searchAgendaResponses(userId, voteSearchFilter));
    }




}
