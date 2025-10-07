package msa.board.comment.service;

import static java.util.function.Predicate.*;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import msa.board.comment.entity.CommentPath;
import msa.board.comment.entity.CommentV2;
import msa.board.comment.repository.CommentRepositoryV2;
import msa.board.comment.service.request.CommentCreateRequestV2;
import msa.board.comment.service.response.CommentResponse;

@Service
@RequiredArgsConstructor
public class CommentServiceV2 {

	private final Snowflake snowflake = new Snowflake();
	private final CommentRepositoryV2 commentRepository;

	@Transactional
	public CommentResponse create(CommentCreateRequestV2 request) {
		CommentV2 parent = findParent(request);
		CommentPath parentCommentPath = parent == null ? CommentPath.create("") : parent.getCommentPath();
		CommentV2 comment = commentRepository.save(
				CommentV2.create(
						snowflake.nextId(),
						request.getContent(),
						request.getArticleId(),
						request.getWriterId(),
						parentCommentPath.createChildCommentPath(
								commentRepository.findDescendantsTopPath(request.getArticleId(),
												parentCommentPath.getPath())
										.orElse(null)
						)
				)
		);

		return CommentResponse.from(comment);
	}

	private CommentV2 findParent(CommentCreateRequestV2 request) {
		String parentPath = request.getParentPath();
		if (parentPath == null) {
			return null;
		}
		return commentRepository.findByPath(parentPath)
				.filter(not(CommentV2::getDeleted))
				.orElseThrow();
	}

	public CommentResponse read(Long commentId) {
		return CommentResponse.from(
				commentRepository.findById(commentId).orElseThrow()
		);
	}

	@Transactional
	public void delete(Long commentId) {
		commentRepository.findById(commentId)
				.filter(not(CommentV2::getDeleted))
				.ifPresent(comment -> {
					if (hasChildren(comment)) {
						comment.delete();
					} else {
						delete(comment);
					}
				});
	}

	private void delete(CommentV2 comment) {

	}

	private boolean hasChildren(CommentV2 comment) {
		return false;
	}

}
