package com.example.puddyBuddy.service;

import com.example.puddyBuddy.domain.Board;
import com.example.puddyBuddy.domain.Comment;
import com.example.puddyBuddy.domain.User;
import com.example.puddyBuddy.dto.comment.CommentCreateRes;
import com.example.puddyBuddy.exception.common.BusinessException;
import com.example.puddyBuddy.exception.common.ErrorCode;
import com.example.puddyBuddy.repository.BoardRepository;
import com.example.puddyBuddy.repository.CommentRepository;
import com.example.puddyBuddy.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public List<Comment> getCommentsByBoardId(Long boardId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        List<Comment> comments = commentRepository.findByBoard_BoardId(boardId, sort);
        return comments;
    }

    public CommentCreateRes createComment(Long boardId, Long userId, String content){
        Comment newComment = new Comment();
        CommentCreateRes commentCreateRes = new CommentCreateRes();

        // board
        Optional<Board> board = boardRepository.findByBoardId(boardId);

        if(board.isEmpty()){
            throw new BusinessException(ErrorCode.NO_EXIST_BOARD);
        }

        newComment.setBoard(board.get());

        // user
        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty()){
            throw new BusinessException(ErrorCode.NO_EXIST_USER);
        }

        newComment.setUser(user.get());

        newComment.setContent(content);

        Long newCommentId = commentRepository.save(newComment).getCommentId();
        commentCreateRes.setCommentId(newCommentId);

        return commentCreateRes;
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}