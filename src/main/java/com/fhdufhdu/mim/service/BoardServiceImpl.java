package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.RequestBoardDto;
import com.fhdufhdu.mim.dto.board.BoardDto;
import com.fhdufhdu.mim.entity.Board;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.entity.RequestBoard;
import com.fhdufhdu.mim.exception.DuplicateBoardException;
import com.fhdufhdu.mim.exception.NotFoundBoardException;
import com.fhdufhdu.mim.exception.NotFoundMovieException;
import com.fhdufhdu.mim.repository.BoardRepository;
import com.fhdufhdu.mim.repository.MovieRepository;
import com.fhdufhdu.mim.repository.RequestBoardRepository;
import com.fhdufhdu.mim.service.util.UtilService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl extends UtilService implements BoardService {
    private final BoardRepository boardRepository;
    private final RequestBoardRepository rBoardRepository;
    private final MovieRepository movieRepository;
    private final static int PAGE_SIZE = 10;

    @Override
    public Page<BoardDto> getAllBoards(int page) {
        // 1대다 문제 발생함, 게시판 불러올때 영화도 불러오기
        PageRequest request = PageRequest.of(page, PAGE_SIZE);
        Page<Board> boards = boardRepository.findAll(request);
        // Page<BoardDto> boardDtos = boards.map(x -> {
        // BoardDto dto = convertToDest(x, BoardDto.class);
        // dto.setMovieName(x.getMovie().getTitle());
        // dto.setMovieDir(x.getMovie().getDirName());
        // return dto;
        // });
        return convertToDests(boards, BoardDto.class);

    }

    @Override
    public Page<BoardDto> getBoardsByTitle(String title, int page) {
        PageRequest request = PageRequest.of(page, PAGE_SIZE);
        Page<Board> boards = boardRepository.findByMovieTitle(title, request);
        return convertToDests(boards, BoardDto.class);
    }

    @Override
    public BoardDto getBoardByMovieId(Long id) {
        return convertToDest(boardRepository.findByMovieId(id).orElseThrow(NotFoundBoardException::new),
                BoardDto.class);
    }

    @Override
    public void shutDownBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(NotFoundBoardException::new);
        board.setIsRemoved(true);
        boardRepository.save(board);
    }

    @Override
    public void countUpBoard(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        RequestBoard rBoard = rBoardRepository.findByMovieId(movieId).orElse(RequestBoard.builder()
                .isConfirmed(false)
                .movie(movie)
                .requestCnt(0)
                .build());
        rBoard.setRequestCnt(rBoard.getRequestCnt() + 1);
        rBoardRepository.save(rBoard);
    }

    @Override
    public void openUpBoard(Long requestId) {
        // 여기 요청게시판하고 영화하고 n + 1문제 발생함
        RequestBoard rBoard = rBoardRepository.findById(requestId).orElseThrow(NotFoundBoardException::new);
        Boolean checkBoard = boardRepository.existsByMovieId(rBoard.getMovie().getId());
        if (checkBoard == true)
            throw new DuplicateBoardException();
        Board board = Board.builder()
                .lastPostingNumber(0L)
                .movie(rBoard.getMovie())
                .build();
        boardRepository.save(board);

        rBoard.setIsConfirmed(true);
        rBoardRepository.save(rBoard);
    }

    @Override
    public void cancelRequestBoard(Long requestId) {
        RequestBoard rBoard = rBoardRepository.findById(requestId).orElseThrow(NotFoundBoardException::new);
        rBoard.setIsConfirmed(true);
        rBoardRepository.save(rBoard);
    }

    @Override
    public Page<RequestBoardDto> getAllRequests(int page) {
        PageRequest request = PageRequest.of(page, PAGE_SIZE);
        return convertToDests(rBoardRepository.findAll(request), RequestBoardDto.class);
    }

    @Override
    public BoardDto getBoardById(Long id) {
        return convertToDest(boardRepository.findById(id).orElseThrow(NotFoundBoardException::new), BoardDto.class);
    }

    @Override
    public RequestBoardDto getRequestById(Long id) {
        return convertToDest(rBoardRepository.findById(id).orElseThrow(NotFoundBoardException::new),
                RequestBoardDto.class);
    }

}
