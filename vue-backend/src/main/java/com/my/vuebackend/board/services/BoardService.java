package com.my.vuebackend.board.services;

import com.my.vuebackend.board.entity.BoardEntity;
import com.my.vuebackend.board.entity.BoardRepository;
import com.my.vuebackend.board.web.dtos.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 목록 가져오기
     */
    public List<BoardDto> getBoardList() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> dtos = new ArrayList<>();

        for (BoardEntity entity : boardEntities) {
            BoardDto dto = BoardDto.builder()
                    .brdNo(entity.getBrdNo())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .regNm(entity.getRegNm())
                    .regDt(entity.getRegDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * 게시글 가져오기
     */
    public BoardDto getBoard(Long id) {
        BoardEntity entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return BoardDto.builder()
                .brdNo(entity.getBrdNo())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .regNm(entity.getRegNm())
                .regDt(entity.getRegDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .build();
    }

    /**
     * 게시글 등록
     */
    public BoardEntity create(BoardDto boardDto) {
        BoardEntity entity = BoardEntity.builder()
                .title(boardDto.getTitle())
                .contents(boardDto.getContents())
                .regNm(boardDto.getRegNm())
                .regDt(LocalDateTime.now())
                .build();
        return boardRepository.save(entity);
    }

    /**
     * 게시글 수정
     */
    public BoardEntity update(BoardDto boardDto) {
        BoardEntity entity = boardRepository.findById(boardDto.getBrdNo()).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        entity.setTitle(boardDto.getTitle());
        entity.setContents(boardDto.getContents());
        return boardRepository.save(entity);
    }

    /**
     * 게시글 삭제
     */
    public void delete(Long id) {
        BoardEntity entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        boardRepository.delete(entity);
    }
}