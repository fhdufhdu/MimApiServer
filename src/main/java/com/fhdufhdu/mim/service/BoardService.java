package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.entity.MovieBoard;
import com.fhdufhdu.mim.entity.RequestBoard;

public interface BoardService {
    /** GET /boards */
    List<MovieBoard> getAllBoards();

    /** GET /boards?title={title} */
    List<MovieBoard> getBoardsByTitle(String title);

    /** GET /boards?movie-id={id} */
    MovieBoard getBoardsByMovieId(Long id);

    /** GET /boards/{id} */
    MovieBoard getBoardById(Long id);

    /** DELETE /boards/{id} */
    void shutDownBoard(Long id);

    /** POST /request-boards/{id} */
    void openUpBoard(Long requestId);

    /** DELETE /request-boards/{id} */
    void cancelRequestBoard(Long requestId);

    /** GET /request-boards */
    List<RequestBoard> getAllRequests();
}
