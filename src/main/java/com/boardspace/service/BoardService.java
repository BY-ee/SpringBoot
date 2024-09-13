package com.boardspace.service;

import com.boardspace.model.CommunityBoard;
import com.boardspace.repository.MemoryBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemoryBoardRepository boardRepository;
    private long id = 1;

    public void writePost(String nickname, CommunityBoard post) {
        post.setId(id++);
        post.setNickname(nickname);
        post.setViewCount(0);
        post.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        post.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        boardRepository.save(post);
    }

    public void updateById(long id, CommunityBoard post) {
        boardRepository.updateById(id, post);
    }

    public void deleteById(long id) {
        boardRepository.deleteById(id);
    }

    public Optional<CommunityBoard> findById(long id) {
        return boardRepository.findById(id);
    }

    public Pagination<CommunityBoard> getPosts(int page, int size) {
        // 0-based 인덱스 방식으로 페이지 변환
        int start = (page - 1) * size;
        long totalElements = boardRepository.count();
        List<CommunityBoard> posts = boardRepository.findPostsByPage(start, size);
        return new Pagination<>(posts, page - 1, size, totalElements);
    }

    public Pagination<CommunityBoard> findPostsByNickName(int page, int size, String nickname) {
        // 0-based 인덱스 방식으로 페이지 변환
        int start = (page - 1) * size;
        long totalElements = boardRepository.countByNickname(nickname);
        List<CommunityBoard> posts = boardRepository.findPostsByPageAndNickname(start, size, nickname);
        return new Pagination<>(posts,page - 1, size, totalElements);
    }

    //public List<Board> findAllByOrderByIdDesc() {
    //    return boardRepository.findAllByOrderByIdDesc();
    //}
}