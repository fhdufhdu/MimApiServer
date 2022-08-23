package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.NotFoundCommentException;
import com.fhdufhdu.mim.exception.NotFoundPostException;
import com.fhdufhdu.mim.exception.NotFoundUserException;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostRepository;
import com.fhdufhdu.mim.repository.UserRepository;
import com.fhdufhdu.mim.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService{
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /** GET /comments/post/{postId}?page={page}&size={size} */
    public Page<Comment.Info> getCommentsByPostId(Long postId, PageParam pageParam){
        Page<Comment.Info> commentInfoList = commentRepository.findByPostId(postId, pageParam.toPageRequest());
        return commentInfoList;
    }

    /** GET /posts/user/{userId}?page={page}&size={size} */
    public Page<Comment.Info> getCommentsByUserId(String userId, PageParam pageParam){
        Page<Comment.Info> commentInfoList = commentRepository.findByUserId(userId, pageParam.toPageRequest());
        return commentInfoList;
    }

    /** PUT /comments/{commentId} */
    public Comment.Info changeComment(Long commentId, Comment.Change comment){
        Comment oldComment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        oldComment.setContent(comment.getContent());
        Comment newComment = commentRepository.save(oldComment);
        return ServiceUtil.convertToDest(newComment, Comment.Info.class);
    }

    /** DELETE /comments/{commentId} */
    public boolean removeComment(Long commentId){
        try {
            commentRepository.deleteById(commentId);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /** POST /comments */
    public Comment.Info writeComment(Comment.Writing comment){
        Post post = postRepository.findById(comment.getPostId()).orElseThrow(NotFoundPostException::new);
        User user = userRepository.findById(comment.getUserId()).orElseThrow(NotFoundUserException::new);
        Comment newComment = Comment.builder()
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .commentGroup(0l)
                .content(comment.getContent())
                .depth(0)
                .post(post)
                .user(user)
                .build();
        return ServiceUtil.convertToDest(commentRepository.save(newComment), Comment.Info.class);
    }
}
