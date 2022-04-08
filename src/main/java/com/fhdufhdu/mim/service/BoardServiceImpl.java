package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.MovieBoardDto;
import com.fhdufhdu.mim.dto.RequestBoardDto;
import com.fhdufhdu.mim.entity.MovieBoard;
import com.fhdufhdu.mim.entity.RequestBoard;
import com.fhdufhdu.mim.repository.BoardRepository;
import com.fhdufhdu.mim.repository.RequestBoardRepository;
import com.fhdufhdu.mim.service.util.UtilService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl extends UtilService implements BoardService {
    private final BoardRepository boardRepository;
    private final RequestBoardRepository rBoardRepository;

    @Override
    public List<MovieBoardDto> getAllBoards() {
        List<MovieBoard> boards = boardRepository.findAll();
        return convertToDests(boards, MovieBoardDto.class);

    }

    @Override
    public List<MovieBoardDto> getBoardsByTitle(String title) {
        List<MovieBoard> boards = boardRepository.findByMovieTitle(title);
        return convertToDests(boards, MovieBoardDto.class);
    }

    @Override
    public MovieBoardDto getBoardsByMovieId(Long id) {
        return convertToDest(boardRepository.findByMovieId(id), MovieBoardDto.class);
    }

    @Override
    public void shutDownBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public void openUpBoard(Long requestId) {
        RequestBoard rBoard = rBoardRepository.findById(requestId).get();
        MovieBoard board = MovieBoard.builder()
                .lastPostingNumber(0L)
                .movie(rBoard.getMovie())
                .build();
        boardRepository.save(board);
    }

    @Override
    public void cancelRequestBoard(Long requestId) {
        rBoardRepository.deleteById(requestId);
    }

    @Override
    public List<RequestBoardDto> getAllRequests() {
        return convertToDests(rBoardRepository.findAll(), RequestBoardDto.class);
    }

    @Override
    public MovieBoardDto getBoardById(Long id) {
        return convertToDest(boardRepository.findById(id).orElseThrow(RuntimeException::new), MovieBoardDto.class);
    }

}
