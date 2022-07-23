package com.agenda.vote.agenda.interfaces;


import com.agenda.vote.agenda.facade.AgendaFacade;
import com.agenda.vote.agenda.interfaces.request.AgendaCreateRequest;
import com.agenda.vote.agenda.interfaces.request.AgendaUpdateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteCreateRequest;
import com.agenda.vote.agenda.interfaces.request.VotingRequest;
import com.agenda.vote.agenda.interfaces.response.AgendaResponse;
import com.agenda.vote.common.annotation.Admin;
import com.agenda.vote.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/v1/agendas")
@RequiredArgsConstructor
public class AgendaApiController {

    private final AgendaFacade agendaFacade;


    @Admin
    @PostMapping
    public CommonResponse<HttpStatus> createAgenda(@Validated @RequestBody AgendaCreateRequest agendaCreateRequest,
                                                   HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.createAgenda(userId, agendaCreateRequest);
        return CommonResponse.success(HttpStatus.CREATED);
    }

    @GetMapping
    public CommonResponse<List<AgendaResponse>> getAgendaResponseList(HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        return CommonResponse.success(agendaFacade.getAgendaResponseList(userId));
    }

    @GetMapping("/{agendaId}")
    public CommonResponse<AgendaResponse> getAgendaResponse(@PathVariable Long agendaId,
                                                            HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        return CommonResponse.success(agendaFacade.getAgendaResponse(userId, agendaId));
    }

    @Admin
    @PatchMapping("/{agendaId}")
    public CommonResponse<HttpStatus> updateAgenda(@PathVariable Long agendaId,
                                                       @Validated @RequestBody AgendaUpdateRequest agendaUpdateRequest,
                                                       HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.updateAgenda(userId, agendaId, agendaUpdateRequest);
        return CommonResponse.success(HttpStatus.OK);
    }

    @Admin
    @DeleteMapping("/{agendaId}")
    public CommonResponse<HttpStatus> deleteAgenda(@PathVariable Long agendaId,
                                                   HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.deleteAgenda(userId, agendaId);
        return CommonResponse.success(HttpStatus.OK);
    }


    @Admin
    @PostMapping("/{agendaId}/votes")
    public CommonResponse<HttpStatus> createVote(@PathVariable Long agendaId,
                                                 @Validated @RequestBody VoteCreateRequest voteCreateRequest,
                                                 HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.createVote(userId, agendaId, voteCreateRequest);
        return CommonResponse.success(HttpStatus.CREATED);
    }

    @PatchMapping("/{agendaId}/votes")
    public CommonResponse<HttpStatus> voting(@PathVariable Long agendaId,
                                             @RequestBody VotingRequest votingRequest,
                                             HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        agendaFacade.voting(userId, agendaId, votingRequest);
        return CommonResponse.success(HttpStatus.OK);
    }




}
