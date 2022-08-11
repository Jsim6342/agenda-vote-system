package com.agenda.vote.agenda.facade;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.agenda.interfaces.AgendaService;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteService;
import com.agenda.vote.agenda.exception.TypeInsufficientConditionException;
import com.agenda.vote.agenda.infrastructure.AgendaRepository;
import com.agenda.vote.agenda.infrastructure.VoteRepository;
import com.agenda.vote.agenda.infrastructure.store.AgendaStoreImpl;
import com.agenda.vote.agenda.infrastructure.store.VoteStoreImpl;
import com.agenda.vote.agenda.interfaces.request.VotingRequest;
import com.agenda.vote.agenda.interfaces.response.AgendaResponse;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.common.entity.VoteType;
import com.agenda.vote.config.security.Role;
import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.domain.user.interfaces.UserService;
import com.agenda.vote.user.infrastructure.UserRepository;
import com.agenda.vote.user.infrastructure.store.UserStoreImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.agenda.vote.common.entity.BaseStatus.ACTIVE;
import static com.agenda.vote.common.entity.VoteType.LIMIT;
import static com.agenda.vote.common.entity.VoteType.NORMAL;
import static com.agenda.vote.config.security.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AgendaFacadeTest {

    @Autowired private AgendaFacade agendaFacade;
    @Autowired private UserStoreImpl userStore;
    @Autowired private AgendaStoreImpl agendaStore;
    @Autowired private VoteStoreImpl voteStore;

    @Autowired private UserRepository userRepository;
    @Autowired private AgendaRepository agendaRepository;
    @Autowired private VoteRepository voteRepository;


    private User userA;
    private User userB;
    private Agenda normalAgenda;
    private Agenda limitAgenda;
    private Vote normalVote;
    private Vote limitVote;

    @BeforeAll()
    void settingTest() {
        User user1 = User.builder()
                            .email("userA@gmail.com")
                            .password("123456789")
                            .votingRightCount(100)
                            .role(ADMIN)
                            .status(ACTIVE)
                            .build();
        userA = userStore.saveUser(user1);

        User user2 = User.builder()
                .email("userB@gmail.com")
                .password("123456789")
                .votingRightCount(100)
                .role(ADMIN)
                .status(ACTIVE)
                .build();
        userB = userStore.saveUser(user2);

        Agenda agenda1 = Agenda.builder()
                                .user(userA)
                                .title("제한 없는 투표 안건 제목")
                                .content("제한 없는 투표 안건 내용")
                                .status(ACTIVE)
                                .build();
        normalAgenda = agendaStore.saveAgenda(agenda1);

        Agenda agenda2 = Agenda.builder()
                .user(userA)
                .title("의결권 선착순 제한 경쟁 투표 안건 제목")
                .content("의결권 선착순 제한 경쟁 투표 안건 내용")
                .status(ACTIVE)
                .build();
        limitAgenda = agendaStore.saveAgenda(agenda2);

        Vote vote1 = Vote.builder()
                .agenda(normalAgenda)
                .type(NORMAL)
                .agreeCount(0L)
                .disagreeCount(0L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .status(ACTIVE)
                .build();
        normalVote = voteStore.saveVote(vote1);

        Vote vote2 = Vote.builder()
                .agenda(limitAgenda)
                .type(LIMIT)
                .agreeCount(0L)
                .disagreeCount(0L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .status(ACTIVE)
                .build();
        limitVote = voteStore.saveVote(vote2);

    }

    @Test
    @DisplayName("normal 타입 투표 테스트")
    void 일반타입_투표테스트() {
        //given
        Long userId = userA.getId();
        Long agendaId = normalAgenda.getId();
        VotingRequest votingRequest1 = new VotingRequest(10L, 0L);
        VotingRequest votingRequest2 = new VotingRequest(10L, 0L);

        //when
        Long beforeAgreeCount = normalVote.getAgreeCount();
        agendaFacade.voting(userId, agendaId, votingRequest1);
        agendaFacade.voting(userId, agendaId, votingRequest2);
        Long afterAgreeCount = agendaFacade.getAgendaResponse(userId, agendaId).getVote().getAgreeCount();

        //then
        Long VotingCount = votingRequest1.getAgreeCount() + votingRequest2.getAgreeCount();
        assertEquals(beforeAgreeCount + VotingCount, afterAgreeCount);
    }

    @Test
    @DisplayName("limit 타입 투표 테스트")
    void 선착순타입_투표테스트() {
        //given
        Long userAId = userA.getId();
        Long userBId = userB.getId();
        Long agendaId = limitAgenda.getId();
        VotingRequest votingRequest1 = new VotingRequest(5L, 0L);
        VotingRequest votingRequest2 = new VotingRequest(5L, 0L);

        //when
        Long beforeAgreeCount = normalVote.getAgreeCount();
        agendaFacade.voting(userAId, agendaId, votingRequest1);
        agendaFacade.voting(userBId, agendaId, votingRequest2);
        Long afterAgreeCount = agendaFacade.getAgendaResponse(userAId, agendaId).getVote().getAgreeCount();

        //then
        Long VotingCount = votingRequest1.getAgreeCount() + votingRequest2.getAgreeCount();
        assertEquals(beforeAgreeCount + VotingCount, afterAgreeCount);
    }

    @Test
    @DisplayName("limit 타입 투표 에러 테스트")
    void 선착순타입_에러_투표테스트() {
        //given
        Long userAId = userA.getId();
        Long userBId = userB.getId();
        Long agendaId = limitAgenda.getId();
        VotingRequest votingRequest1 = new VotingRequest(10L, 0L);
        VotingRequest votingRequest2 = new VotingRequest(10L, 0L);

        //when, then
        agendaFacade.voting(userAId, agendaId, votingRequest1);
        assertThrows(TypeInsufficientConditionException.class, () -> agendaFacade.voting(userBId, agendaId, votingRequest2));
    }

    @AfterAll
    void endTest() {
        voteRepository.delete(normalVote);
        voteRepository.delete(limitVote);
        agendaRepository.delete(normalAgenda);
        agendaRepository.delete(limitAgenda);
        userRepository.delete(userA);
        userRepository.delete(userB);
    }

//    @Test
//    @DisplayName("normal 타입 동시성 투표 테스트")
//    void 일반타입_동시성_투표테스트() throws InterruptedException {
//
//        //given
//        Long userId = userA.getId();
//        Long agendaId = normalAgenda.getId();
//
//        // when
//        Long beforeAgreeCount = normalVote.getAgreeCount();
//        int repeatNumber = 10;
//
//        //Thread mainThread = Thread.currentThread();
//        for (int i = 0; i < repeatNumber; i++) {
//            //if (i == 0)  mainThread.wait();
//            Thread thread = new Thread(() -> {
//                VotingRequest votingRequest = new VotingRequest(1L, 0L);
//                agendaFacade.voting(userId, agendaId, votingRequest);
//            });
//            thread.start();
//            //if (i == repeatNumber) mainThread.notify();
//        }
//
//        //then
//        Long afterAgreeCount = agendaFacade.getAgendaResponse(userId, agendaId).getVote().getAgreeCount();
//        assertEquals(beforeAgreeCount + repeatNumber, afterAgreeCount);
//    }

}