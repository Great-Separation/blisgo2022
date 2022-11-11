package com.blisgo.service.impl;

import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.repository.ReplyRepository;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.ReplyDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ReplyServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ReplyServiceImplTest {
    @MockBean
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyServiceImpl replyServiceImpl;

    /**
     * Method under test: {@link ReplyServiceImpl#findReply(BoardDTO)}
     */
    @Test
    void testFindReply() {
        when(replyRepository.selectReplyInnerJoinAccount(any())).thenReturn(new ArrayList<>());
        assertTrue(replyServiceImpl.findReply(new BoardDTO()).isEmpty());
        verify(replyRepository).selectReplyInnerJoinAccount(any());
    }

    /**
     * Method under test: {@link ReplyServiceImpl#findReply(BoardDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindReply2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.time.LocalDateTime.until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)" because "localDateTime" is null
        //       at com.blisgo.util.TimeManager.calcTimeDiff(TimeManager.java:31)
        //       at com.blisgo.web.dto.ReplyDTO.<init>(ReplyDTO.java:37)
        //       at com.blisgo.web.dto.ReplyDTO$ReplyDTOBuilder.build(ReplyDTO.java:28)
        //       at com.blisgo.domain.mapper.ReplyMapperImpl.toDTO(ReplyMapperImpl.java:46)
        //       at com.blisgo.domain.mapper.ReplyMapperImpl.toDTOList(ReplyMapperImpl.java:57)
        //       at com.blisgo.service.impl.ReplyServiceImpl.findReply(ReplyServiceImpl.java:30)
        //   In order to prevent findReply(BoardDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findReply(BoardDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        Reply reply = new Reply();
        reply.preUpdate();

        ArrayList<Reply> replyList = new ArrayList<>();
        replyList.add(reply);
        when(replyRepository.selectReplyInnerJoinAccount(any())).thenReturn(replyList);
        replyServiceImpl.findReply(new BoardDTO());
    }

    /**
     * Method under test: {@link ReplyServiceImpl#addReply(ReplyDTO, BoardDTO, AccountDTO)}
     */
    @Test
    void testAddReply() {
        when(replyRepository.insertReply(any())).thenReturn(true);
        when(replyRepository.updateReplyCount(any(), anyBoolean())).thenReturn(true);
        ReplyDTO replyDTO = new ReplyDTO();
        BoardDTO boardDTO = new BoardDTO();
        assertTrue(replyServiceImpl.addReply(replyDTO, boardDTO, new AccountDTO()));
        verify(replyRepository).insertReply(any());
        verify(replyRepository).updateReplyCount(any(), anyBoolean());
    }

    /**
     * Method under test: {@link ReplyServiceImpl#addReply(ReplyDTO, BoardDTO, AccountDTO)}
     */
    @Test
    void testAddReply2() {
        when(replyRepository.insertReply(any())).thenReturn(false);
        when(replyRepository.updateReplyCount(any(), anyBoolean())).thenReturn(true);
        ReplyDTO replyDTO = new ReplyDTO();
        BoardDTO boardDTO = new BoardDTO();
        assertFalse(replyServiceImpl.addReply(replyDTO, boardDTO, new AccountDTO()));
        verify(replyRepository).insertReply(any());
        verify(replyRepository).updateReplyCount(any(), anyBoolean());
    }

    /**
     * Method under test: {@link ReplyServiceImpl#addReply(ReplyDTO, BoardDTO, AccountDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddReply3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.domain.entity.Reply.getReplyNo()" because "reply" is null
        //       at com.blisgo.service.impl.ReplyServiceImpl.addReply(ReplyServiceImpl.java:39)
        //   In order to prevent addReply(ReplyDTO, BoardDTO, AccountDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   addReply(ReplyDTO, BoardDTO, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        when(replyRepository.insertReply(any())).thenReturn(true);
        when(replyRepository.updateReplyCount(any(), anyBoolean())).thenReturn(true);
        BoardDTO boardDTO = new BoardDTO();
        replyServiceImpl.addReply(null, boardDTO, new AccountDTO());
    }

    /**
     * Method under test: {@link ReplyServiceImpl#removeReply(ReplyDTO, BoardDTO)}
     */
    @Test
    void testRemoveReply() {
        when(replyRepository.deleteReply(any())).thenReturn(true);
        when(replyRepository.updateReplyCount(any(), anyBoolean())).thenReturn(true);
        ReplyDTO replyDTO = new ReplyDTO();
        assertTrue(replyServiceImpl.removeReply(replyDTO, new BoardDTO()));
        verify(replyRepository).deleteReply(any());
        verify(replyRepository).updateReplyCount(any(), anyBoolean());
    }

    /**
     * Method under test: {@link ReplyServiceImpl#removeReply(ReplyDTO, BoardDTO)}
     */
    @Test
    void testRemoveReply2() {
        when(replyRepository.deleteReply(any())).thenReturn(false);
        when(replyRepository.updateReplyCount(any(), anyBoolean())).thenReturn(true);
        ReplyDTO replyDTO = new ReplyDTO();
        assertFalse(replyServiceImpl.removeReply(replyDTO, new BoardDTO()));
        verify(replyRepository).deleteReply(any());
        verify(replyRepository).updateReplyCount(any(), anyBoolean());
    }

    /**
     * Method under test: {@link ReplyServiceImpl#removeReply(ReplyDTO, BoardDTO)}
     */
    @Test
    void testRemoveReply3() {
        when(replyRepository.deleteReply(any())).thenReturn(true);
        when(replyRepository.updateReplyCount(any(), anyBoolean())).thenReturn(true);
        assertTrue(replyServiceImpl.removeReply(null, new BoardDTO()));
        verify(replyRepository).deleteReply(any());
        verify(replyRepository).updateReplyCount(any(), anyBoolean());
    }
}

