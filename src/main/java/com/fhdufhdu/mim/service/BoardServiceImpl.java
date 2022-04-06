package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.entity.MovieBoard;
import com.fhdufhdu.mim.entity.RequestBoard;
import com.fhdufhdu.mim.repository.BoardRepository;
import com.fhdufhdu.mim.repository.RequestBoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private RequestBoardRepository rBoardRepository;

    @Override
    public List<MovieBoard> getAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    public List<MovieBoard> getBoardsByTitle(String title) {
        return boardRepository.findByMovieTitle(title);
    }

    @Override
    public MovieBoard getBoardsByMovieId(Long id) {
        return boardRepository.findByMovieId(id);
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
    public List<RequestBoard> getAllRequests() {
        return rBoardRepository.findAll();
    }

    @Override
    public MovieBoard getBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
