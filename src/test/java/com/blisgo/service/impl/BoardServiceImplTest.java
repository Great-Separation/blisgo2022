package com.blisgo.service.impl;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Board;
import com.blisgo.domain.repository.BoardRepository;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BoardServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BoardServiceImplTest {
    @MockBean
    private BoardRepository boardRepository;

    @Autowired
    private BoardServiceImpl boardServiceImpl;

    /**
     * Method under test: {@link BoardServiceImpl#addBoard(BoardDTO, AccountDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddBoard() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.CharSequence.length()" because "this.text" is null
        //       at java.util.regex.Matcher.getTextLength(Matcher.java:1769)
        //       at java.util.regex.Matcher.reset(Matcher.java:415)
        //       at java.util.regex.Matcher.<init>(Matcher.java:252)
        //       at java.util.regex.Pattern.matcher(Pattern.java:1134)
        //       at com.blisgo.util.HtmlContentParse.getImgSrc(HtmlContentParse.java:50)
        //       at com.blisgo.util.HtmlContentParse.parseThumbnail(HtmlContentParse.java:16)
        //       at com.blisgo.service.impl.BoardServiceImpl.addBoard(BoardServiceImpl.java:35)
        //   In order to prevent addBoard(BoardDTO, AccountDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   addBoard(BoardDTO, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        BoardDTO boardDTO = new BoardDTO();
        boardServiceImpl.addBoard(boardDTO, new AccountDTO());
    }

    /**
     * Method under test: {@link BoardServiceImpl#addBoard(BoardDTO, AccountDTO)}
     */
    @Test
    void testAddBoard2() {
        when(boardRepository.insertBoard(any())).thenReturn(true);
        Account account = new Account();
        LocalDateTime createdDate = LocalDateTime.of(1, 1, 1, 1, 1);
        BoardDTO boardDTO = new BoardDTO(1, account, "Dr", "<imgUsrc=UU>", "Not all who wander are lost", 1, 1, 3,
                "<imgUsrc=UU>", createdDate, LocalDateTime.of(1, 1, 1, 1, 1));

        assertTrue(boardServiceImpl.addBoard(boardDTO, new AccountDTO()));
        verify(boardRepository).insertBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#addBoard(BoardDTO, AccountDTO)}
     */
    @Test
    void testAddBoard3() {
        when(boardRepository.insertBoard(any())).thenReturn(false);
        Account account = new Account();
        LocalDateTime createdDate = LocalDateTime.of(1, 1, 1, 1, 1);
        BoardDTO boardDTO = new BoardDTO(1, account, "Dr", "<imgUsrc=UU>", "Not all who wander are lost", 1, 1, 3,
                "<imgUsrc=UU>", createdDate, LocalDateTime.of(1, 1, 1, 1, 1));

        assertFalse(boardServiceImpl.addBoard(boardDTO, new AccountDTO()));
        verify(boardRepository).insertBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#addBoard(BoardDTO, AccountDTO)}
     */
    @Test
    void testAddBoard4() {
        when(boardRepository.insertBoard(any())).thenReturn(true);
        Account account = new Account();
        LocalDateTime createdDate = LocalDateTime.of(1, 1, 1, 1, 1);
        BoardDTO boardDTO = new BoardDTO(1, account, "Dr", "<imgUsrc=UU>", "<imgUsrc=UU>", 1, 1, 3, "<imgUsrc=UU>",
                createdDate, LocalDateTime.of(1, 1, 1, 1, 1));

        assertTrue(boardServiceImpl.addBoard(boardDTO, new AccountDTO()));
        verify(boardRepository).insertBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#addBoard(BoardDTO, AccountDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddBoard5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.domain.entity.Board.getBdContent()" because "board" is null
        //       at com.blisgo.service.impl.BoardServiceImpl.addBoard(BoardServiceImpl.java:35)
        //   In order to prevent addBoard(BoardDTO, AccountDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   addBoard(BoardDTO, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        when(boardRepository.insertBoard(any())).thenReturn(true);
        boardServiceImpl.addBoard(null, new AccountDTO());
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoards()}
     */
    @Test
    void testFindBoards() {
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        assertTrue(boardServiceImpl.findBoards().isEmpty());
        verify(boardRepository).selectBoardList(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoards()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindBoards2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent findBoards()
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findBoards().
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        boardDTOList.add(new BoardDTO());
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        boardServiceImpl.findBoards();
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoards()}
     */
    @Test
    void testFindBoards3() {
        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        Account account = new Account();
        LocalDateTime createdDate = LocalDateTime.of(12, 12, 12, 12, 1);
        boardDTOList.add(new BoardDTO(12, account, "Dr", "<imgU>", "Not all who wander are lost", 12, 12, 3, "<imgU>",
                createdDate, LocalDateTime.of(12, 12, 12, 12, 1)));
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        assertEquals(1, boardServiceImpl.findBoards().size());
        verify(boardRepository).selectBoardList(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoards()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindBoards4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.web.dto.BoardDTO.getBdContent()" because "b" is null
        //       at com.blisgo.service.impl.BoardServiceImpl.findBoards(BoardServiceImpl.java:52)
        //   In order to prevent findBoards()
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findBoards().
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        boardDTOList.add(null);
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        boardServiceImpl.findBoards();
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoards()}
     */
    @Test
    void testFindBoards5() {
        Account account = new Account();
        account.preUpdate();
        BoardDTO boardDTO = mock(BoardDTO.class);
        when(boardDTO.getAccount()).thenReturn(account);
        when(boardDTO.getBdFavorite()).thenReturn(1);
        when(boardDTO.getBdNo()).thenReturn(1);
        when(boardDTO.getBdReplyCount()).thenReturn(3);
        when(boardDTO.getBdViews()).thenReturn(1);
        when(boardDTO.getBdCategory()).thenReturn("Bd Category");
        when(boardDTO.getBdThumbnail()).thenReturn("Bd Thumbnail");
        when(boardDTO.getBdTitle()).thenReturn("Dr");
        when(boardDTO.getCreatedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(boardDTO.getModifiedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(boardDTO.getBdContent()).thenReturn("Not all who wander are lost");

        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        boardDTOList.add(boardDTO);
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        assertEquals(1, boardServiceImpl.findBoards().size());
        verify(boardRepository).selectBoardList(anyInt(), anyInt());
        verify(boardDTO).getAccount();
        verify(boardDTO).getBdFavorite();
        verify(boardDTO).getBdNo();
        verify(boardDTO).getBdReplyCount();
        verify(boardDTO).getBdViews();
        verify(boardDTO).getBdCategory();
        verify(boardDTO).getBdContent();
        verify(boardDTO).getBdThumbnail();
        verify(boardDTO).getBdTitle();
        verify(boardDTO).getCreatedDate();
        verify(boardDTO).getModifiedDate();
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoards()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindBoards6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.time.LocalDateTime.until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)" because "localDateTime" is null
        //       at com.blisgo.util.TimeManager.calcTimeDiff(TimeManager.java:31)
        //       at com.blisgo.web.dto.BoardDTO.<init>(BoardDTO.java:51)
        //       at com.blisgo.web.dto.BoardDTO$BoardDTOBuilder.build(BoardDTO.java:36)
        //       at com.blisgo.web.dto.BoardDTO.selectBoardFilterContentImage(BoardDTO.java:58)
        //       at com.blisgo.service.impl.BoardServiceImpl.findBoards(BoardServiceImpl.java:53)
        //   In order to prevent findBoards()
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findBoards().
        //   See https://diff.blue/R013 to resolve this issue.

        Account account = new Account();
        account.preUpdate();
        BoardDTO boardDTO = mock(BoardDTO.class);
        when(boardDTO.getAccount()).thenReturn(account);
        when(boardDTO.getBdFavorite()).thenReturn(1);
        when(boardDTO.getBdNo()).thenReturn(1);
        when(boardDTO.getBdReplyCount()).thenReturn(3);
        when(boardDTO.getBdViews()).thenReturn(1);
        when(boardDTO.getBdCategory()).thenReturn("Bd Category");
        when(boardDTO.getBdThumbnail()).thenReturn("Bd Thumbnail");
        when(boardDTO.getBdTitle()).thenReturn("Dr");
        when(boardDTO.getCreatedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(boardDTO.getModifiedDate()).thenReturn(null);
        when(boardDTO.getBdContent()).thenReturn("Not all who wander are lost");

        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        boardDTOList.add(boardDTO);
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        boardServiceImpl.findBoards();
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoardMore()}
     */
    @Test
    void testFindBoardMore() {
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        assertTrue(boardServiceImpl.findBoardMore().isEmpty());
        verify(boardRepository).selectBoardList(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoardMore()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindBoardMore2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent findBoardMore()
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findBoardMore().
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        boardDTOList.add(new BoardDTO());
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        boardServiceImpl.findBoardMore();
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoardMore()}
     */
    @Test
    void testFindBoardMore3() {
        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        Account account = new Account();
        LocalDateTime createdDate = LocalDateTime.of(12, 12, 12, 12, 1);
        boardDTOList.add(new BoardDTO(12, account, "Dr", "<imgU>", "Not all who wander are lost", 12, 12, 3, "<imgU>",
                createdDate, LocalDateTime.of(12, 12, 12, 12, 1)));
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        assertEquals(1, boardServiceImpl.findBoardMore().size());
        verify(boardRepository).selectBoardList(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoardMore()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindBoardMore4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.web.dto.BoardDTO.getBdContent()" because "b" is null
        //       at com.blisgo.service.impl.BoardServiceImpl.findBoardMore(BoardServiceImpl.java:74)
        //   In order to prevent findBoardMore()
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findBoardMore().
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        boardDTOList.add(null);
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        boardServiceImpl.findBoardMore();
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoardMore()}
     */
    @Test
    void testFindBoardMore5() {
        Account account = new Account();
        account.preUpdate();
        BoardDTO boardDTO = mock(BoardDTO.class);
        when(boardDTO.getAccount()).thenReturn(account);
        when(boardDTO.getBdFavorite()).thenReturn(1);
        when(boardDTO.getBdNo()).thenReturn(1);
        when(boardDTO.getBdReplyCount()).thenReturn(3);
        when(boardDTO.getBdViews()).thenReturn(1);
        when(boardDTO.getBdCategory()).thenReturn("Bd Category");
        when(boardDTO.getBdThumbnail()).thenReturn("Bd Thumbnail");
        when(boardDTO.getBdTitle()).thenReturn("Dr");
        when(boardDTO.getCreatedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(boardDTO.getModifiedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(boardDTO.getBdContent()).thenReturn("Not all who wander are lost");

        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        boardDTOList.add(boardDTO);
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        assertEquals(1, boardServiceImpl.findBoardMore().size());
        verify(boardRepository).selectBoardList(anyInt(), anyInt());
        verify(boardDTO).getAccount();
        verify(boardDTO).getBdFavorite();
        verify(boardDTO).getBdNo();
        verify(boardDTO).getBdReplyCount();
        verify(boardDTO).getBdViews();
        verify(boardDTO).getBdCategory();
        verify(boardDTO).getBdContent();
        verify(boardDTO).getBdThumbnail();
        verify(boardDTO).getBdTitle();
        verify(boardDTO).getCreatedDate();
        verify(boardDTO).getModifiedDate();
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoardMore()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindBoardMore6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.time.LocalDateTime.until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)" because "localDateTime" is null
        //       at com.blisgo.util.TimeManager.calcTimeDiff(TimeManager.java:31)
        //       at com.blisgo.web.dto.BoardDTO.<init>(BoardDTO.java:51)
        //       at com.blisgo.web.dto.BoardDTO$BoardDTOBuilder.build(BoardDTO.java:36)
        //       at com.blisgo.web.dto.BoardDTO.selectBoardFilterContentImage(BoardDTO.java:58)
        //       at com.blisgo.service.impl.BoardServiceImpl.findBoardMore(BoardServiceImpl.java:75)
        //   In order to prevent findBoardMore()
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findBoardMore().
        //   See https://diff.blue/R013 to resolve this issue.

        Account account = new Account();
        account.preUpdate();
        BoardDTO boardDTO = mock(BoardDTO.class);
        when(boardDTO.getAccount()).thenReturn(account);
        when(boardDTO.getBdFavorite()).thenReturn(1);
        when(boardDTO.getBdNo()).thenReturn(1);
        when(boardDTO.getBdReplyCount()).thenReturn(3);
        when(boardDTO.getBdViews()).thenReturn(1);
        when(boardDTO.getBdCategory()).thenReturn("Bd Category");
        when(boardDTO.getBdThumbnail()).thenReturn("Bd Thumbnail");
        when(boardDTO.getBdTitle()).thenReturn("Dr");
        when(boardDTO.getCreatedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(boardDTO.getModifiedDate()).thenReturn(null);
        when(boardDTO.getBdContent()).thenReturn("Not all who wander are lost");

        ArrayList<BoardDTO> boardDTOList = new ArrayList<>();
        boardDTOList.add(boardDTO);
        when(boardRepository.selectBoardList(anyInt(), anyInt())).thenReturn(boardDTOList);
        boardServiceImpl.findBoardMore();
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoard(BoardDTO)}
     */
    @Test
    void testFindBoard() {
        Board board = new Board();
        board.preUpdate();
        when(boardRepository.selectBoard(any())).thenReturn(board);
        BoardDTO actualFindBoardResult = boardServiceImpl.findBoard(new BoardDTO());
        assertNull(actualFindBoardResult.getAccount());
        assertEquals("0초 전", actualFindBoardResult.getTimeDiff());
        assertNull(actualFindBoardResult.getCreatedDate());
        assertNull(actualFindBoardResult.getBdViews());
        assertNull(actualFindBoardResult.getBdTitle());
        assertNull(actualFindBoardResult.getBdThumbnail());
        assertNull(actualFindBoardResult.getBdReplyCount());
        assertNull(actualFindBoardResult.getBdNo());
        assertNull(actualFindBoardResult.getBdFavorite());
        assertNull(actualFindBoardResult.getBdContent());
        assertNull(actualFindBoardResult.getBdCategory());
        verify(boardRepository).selectBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoard(BoardDTO)}
     */
    @Test
    void testFindBoard2() {
        Account account = new Account();
        account.preUpdate();
        Board board = mock(Board.class);
        when(board.getAccount()).thenReturn(account);
        when(board.getBdFavorite()).thenReturn(1);
        when(board.getBdNo()).thenReturn(1);
        when(board.getBdReplyCount()).thenReturn(3);
        when(board.getBdViews()).thenReturn(1);
        when(board.getBdCategory()).thenReturn("Bd Category");
        when(board.getBdContent()).thenReturn("Not all who wander are lost");
        when(board.getBdThumbnail()).thenReturn("Bd Thumbnail");
        when(board.getBdTitle()).thenReturn("Dr");
        when(board.getCreatedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(board.getModifiedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        doNothing().when(board).preUpdate();
        board.preUpdate();
        when(boardRepository.selectBoard(any())).thenReturn(board);
        BoardDTO actualFindBoardResult = boardServiceImpl.findBoard(new BoardDTO());
        assertSame(account, actualFindBoardResult.getAccount());
        assertEquals("Bd Category", actualFindBoardResult.getBdCategory());
        assertEquals(1, actualFindBoardResult.getBdFavorite().intValue());
        assertEquals("Dr", actualFindBoardResult.getBdTitle());
        assertEquals(1, actualFindBoardResult.getBdViews().intValue());
        assertEquals(1, actualFindBoardResult.getBdNo().intValue());
        assertEquals("0001-01-01", actualFindBoardResult.getCreatedDate().toLocalDate().toString());
        assertEquals("0001-01-01", actualFindBoardResult.getModifiedDate().toLocalDate().toString());
        assertEquals("Not all who wander are lost", actualFindBoardResult.getBdContent());
        assertEquals(3, actualFindBoardResult.getBdReplyCount().intValue());
        assertEquals("Bd Thumbnail", actualFindBoardResult.getBdThumbnail());
        verify(boardRepository).selectBoard(any());
        verify(board).getAccount();
        verify(board).getBdFavorite();
        verify(board).getBdNo();
        verify(board).getBdReplyCount();
        verify(board).getBdViews();
        verify(board).getBdCategory();
        verify(board).getBdContent();
        verify(board).getBdThumbnail();
        verify(board).getBdTitle();
        verify(board).getCreatedDate();
        verify(board).getModifiedDate();
        verify(board).preUpdate();
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoard(BoardDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindBoard3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.time.LocalDateTime.until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)" because "localDateTime" is null
        //       at com.blisgo.util.TimeManager.calcTimeDiff(TimeManager.java:31)
        //       at com.blisgo.web.dto.BoardDTO.<init>(BoardDTO.java:51)
        //       at com.blisgo.web.dto.BoardDTO$BoardDTOBuilder.build(BoardDTO.java:36)
        //       at com.blisgo.domain.mapper.BoardMapperImpl.toDTO(BoardMapperImpl.java:57)
        //       at com.blisgo.domain.mapper.BoardMapperImpl.toDTO(BoardMapperImpl.java:9)
        //       at com.blisgo.service.impl.BoardServiceImpl.findBoard(BoardServiceImpl.java:86)
        //   In order to prevent findBoard(BoardDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findBoard(BoardDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        Account account = new Account();
        account.preUpdate();
        Board board = mock(Board.class);
        when(board.getAccount()).thenReturn(account);
        when(board.getBdFavorite()).thenReturn(1);
        when(board.getBdNo()).thenReturn(1);
        when(board.getBdReplyCount()).thenReturn(3);
        when(board.getBdViews()).thenReturn(1);
        when(board.getBdCategory()).thenReturn("Bd Category");
        when(board.getBdContent()).thenReturn("Not all who wander are lost");
        when(board.getBdThumbnail()).thenReturn("Bd Thumbnail");
        when(board.getBdTitle()).thenReturn("Dr");
        when(board.getCreatedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(board.getModifiedDate()).thenReturn(null);
        doNothing().when(board).preUpdate();
        board.preUpdate();
        when(boardRepository.selectBoard(any())).thenReturn(board);
        boardServiceImpl.findBoard(new BoardDTO());
    }

    /**
     * Method under test: {@link BoardServiceImpl#findBoard(BoardDTO)}
     */
    @Test
    void testFindBoard4() {
        Account account = new Account();
        account.preUpdate();
        Board board = mock(Board.class);
        when(board.getAccount()).thenReturn(account);
        when(board.getBdFavorite()).thenReturn(1);
        when(board.getBdNo()).thenReturn(1);
        when(board.getBdReplyCount()).thenReturn(3);
        when(board.getBdViews()).thenReturn(1);
        when(board.getBdCategory()).thenReturn("Bd Category");
        when(board.getBdContent()).thenReturn("Not all who wander are lost");
        when(board.getBdThumbnail()).thenReturn("Bd Thumbnail");
        when(board.getBdTitle()).thenReturn("Dr");
        when(board.getCreatedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(board.getModifiedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        doNothing().when(board).preUpdate();
        board.preUpdate();
        when(boardRepository.selectBoard(any())).thenReturn(board);
        BoardDTO actualFindBoardResult = boardServiceImpl.findBoard(null);
        assertSame(account, actualFindBoardResult.getAccount());
        assertEquals("Bd Category", actualFindBoardResult.getBdCategory());
        assertEquals(1, actualFindBoardResult.getBdFavorite().intValue());
        assertEquals("Dr", actualFindBoardResult.getBdTitle());
        assertEquals(1, actualFindBoardResult.getBdViews().intValue());
        assertEquals(1, actualFindBoardResult.getBdNo().intValue());
        assertEquals("0001-01-01", actualFindBoardResult.getCreatedDate().toLocalDate().toString());
        assertEquals("0001-01-01", actualFindBoardResult.getModifiedDate().toLocalDate().toString());
        assertEquals("Not all who wander are lost", actualFindBoardResult.getBdContent());
        assertEquals(3, actualFindBoardResult.getBdReplyCount().intValue());
        assertEquals("Bd Thumbnail", actualFindBoardResult.getBdThumbnail());
        verify(boardRepository).selectBoard(any());
        verify(board).getAccount();
        verify(board).getBdFavorite();
        verify(board).getBdNo();
        verify(board).getBdReplyCount();
        verify(board).getBdViews();
        verify(board).getBdCategory();
        verify(board).getBdContent();
        verify(board).getBdThumbnail();
        verify(board).getBdTitle();
        verify(board).getCreatedDate();
        verify(board).getModifiedDate();
        verify(board).preUpdate();
    }

    /**
     * Method under test: {@link BoardServiceImpl#removeBoard(BoardDTO)}
     */
    @Test
    void testRemoveBoard() {
        when(boardRepository.deleteBoard(any())).thenReturn(true);
        assertTrue(boardServiceImpl.removeBoard(new BoardDTO()));
        verify(boardRepository).deleteBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#removeBoard(BoardDTO)}
     */
    @Test
    void testRemoveBoard2() {
        when(boardRepository.deleteBoard(any())).thenReturn(false);
        assertFalse(boardServiceImpl.removeBoard(new BoardDTO()));
        verify(boardRepository).deleteBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#removeBoard(BoardDTO)}
     */
    @Test
    void testRemoveBoard3() {
        when(boardRepository.deleteBoard(any())).thenReturn(true);
        assertTrue(boardServiceImpl.removeBoard(null));
        verify(boardRepository).deleteBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#countBoardViews(BoardDTO)}
     */
    @Test
    void testCountBoardViews() {
        when(boardRepository.updateBoardViews(any())).thenReturn(true);
        assertTrue(boardServiceImpl.countBoardViews(new BoardDTO()));
        verify(boardRepository).updateBoardViews(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#countBoardViews(BoardDTO)}
     */
    @Test
    void testCountBoardViews2() {
        when(boardRepository.updateBoardViews(any())).thenReturn(false);
        assertFalse(boardServiceImpl.countBoardViews(new BoardDTO()));
        verify(boardRepository).updateBoardViews(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#countBoardViews(BoardDTO)}
     */
    @Test
    void testCountBoardViews3() {
        when(boardRepository.updateBoardViews(any())).thenReturn(true);
        assertTrue(boardServiceImpl.countBoardViews(null));
        verify(boardRepository).updateBoardViews(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#modifyBoard(BoardDTO)}
     */
    @Test
    void testModifyBoard() {
        when(boardRepository.updateBoard(any())).thenReturn(true);
        assertTrue(boardServiceImpl.modifyBoard(new BoardDTO()));
        verify(boardRepository).updateBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#modifyBoard(BoardDTO)}
     */
    @Test
    void testModifyBoard2() {
        when(boardRepository.updateBoard(any())).thenReturn(false);
        assertFalse(boardServiceImpl.modifyBoard(new BoardDTO()));
        verify(boardRepository).updateBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#modifyBoard(BoardDTO)}
     */
    @Test
    void testModifyBoard3() {
        when(boardRepository.updateBoard(any())).thenReturn(true);
        assertTrue(boardServiceImpl.modifyBoard(null));
        verify(boardRepository).updateBoard(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#countBoardFavorite(BoardDTO)}
     */
    @Test
    void testCountBoardFavorite() {
        when(boardRepository.updateBoardFavorite(any())).thenReturn(true);
        assertTrue(boardServiceImpl.countBoardFavorite(new BoardDTO()));
        verify(boardRepository).updateBoardFavorite(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#countBoardFavorite(BoardDTO)}
     */
    @Test
    void testCountBoardFavorite2() {
        when(boardRepository.updateBoardFavorite(any())).thenReturn(false);
        assertFalse(boardServiceImpl.countBoardFavorite(new BoardDTO()));
        verify(boardRepository).updateBoardFavorite(any());
    }

    /**
     * Method under test: {@link BoardServiceImpl#countBoardFavorite(BoardDTO)}
     */
    @Test
    void testCountBoardFavorite3() {
        when(boardRepository.updateBoardFavorite(any())).thenReturn(true);
        assertTrue(boardServiceImpl.countBoardFavorite(null));
        verify(boardRepository).updateBoardFavorite(any());
    }
}

