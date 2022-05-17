package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.BoardDto;
import com.fhdufhdu.mim.dto.RequestBoardDto;

import org.springframework.data.domain.Page;

public interface BoardService {
    /**
     * GET /boards?page={page}
     * 영화 이름
     */
    Page<BoardDto> getAllBoards(int page);

    /** GET /boards/title/{title}?page={page} */
    Page<BoardDto> getBoardsByTitle(String title, int page);

    /** GET /boards/movie/{movieId} */
    BoardDto getBoardByMovieId(Long movieId);

    /** GET /boards/{id} */
    BoardDto getBoardById(Long id);

    /** DELETE /boards/{id} */
    void shutDownBoard(Long id);

    /** POST /request-boards/movie/{movieId} */
    void countUpBoard(Long movieId);

    /** POST /request-boards/{id} */
    void openUpBoard(Long requestId);

    /** DELETE /request-boards/{id} */
    void cancelRequestBoard(Long requestId);

    /** GET /request-boards?page={page} */
    Page<RequestBoardDto> getAllRequests(int page);

    RequestBoardDto getRequestById(Long id);
}
