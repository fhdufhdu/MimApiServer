package com.fhdufhdu.mim.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fhdufhdu.mim.repository.BoardRepository;
import com.fhdufhdu.mim.repository.RequestBoardRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private RequestBoardRepository rBoardRepository;

    @InjectMocks
    private BoardServiceImpl boardService;

    @Test
    void 모든게시판_가져오기() {

    }
}
