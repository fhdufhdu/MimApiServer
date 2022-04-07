package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.MovieBoardDto;
import com.fhdufhdu.mim.dto.RequestBoardDto;

public interface BoardService {
    /** GET /boards */
    List<MovieBoardDto> getAllBoards();

    /** GET /boards?title={title} */
    List<MovieBoardDto> getBoardsByTitle(String title);

    /** GET /boards?movie-id={id} */
    MovieBoardDto getBoardsByMovieId(Long id);

    /** GET /boards/{id} */
    MovieBoardDto getBoardById(Long id);

    /** DELETE /boards/{id} */
    void shutDownBoard(Long id);

    /** POST /request-boards/{id} */
    void openUpBoard(Long requestId);

    /** DELETE /request-boards/{id} */
    void cancelRequestBoard(Long requestId);

    /** GET /request-boards */
    List<RequestBoardDto> getAllRequests();
}
