package msa.board.comment.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "comment")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
	@Id
	private Long commentId;
	private String content;
	private Long parentCommentId;
	private Long articleId; //shard key
	private Long writerId;
	private Boolean deleted;
	private LocalDateTime createdAt;

	private Comment(final Long commentId, final String content, final Long parentCommentId, final Long articleId, final Long writerId, final Boolean deleted,
			final LocalDateTime createdAt) {
		this.commentId = commentId;
		this.content = content;
		this.parentCommentId = parentCommentId;
		this.articleId = articleId;
		this.writerId = writerId;
		this.deleted = deleted;
		this.createdAt = createdAt;
	}

	public static Comment create(Long commentId, String content, Long parentCommentId, Long articleId, Long writerId) {
		return new Comment(
				commentId,
				content,
				parentCommentId == null ? commentId : parentCommentId,
				articleId,
				writerId,
				false,
				LocalDateTime.now()
		);
	}

	public boolean isRoot() {
		return parentCommentId.longValue() == commentId;
	}

	public void delete() {
		this.deleted = true;
	}
}
