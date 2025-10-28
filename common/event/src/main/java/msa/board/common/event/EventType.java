package msa.board.common.event;

import msa.board.common.event.payload.ArticleCreatedEventPayload;
import msa.board.common.event.payload.ArticleDeletedEventPayload;
import msa.board.common.event.payload.ArticleLikedEventPayload;
import msa.board.common.event.payload.ArticleUnlikedEventPayload;
import msa.board.common.event.payload.ArticleUpdatedEventPayload;
import msa.board.common.event.payload.ArticleViewedEventPayload;
import msa.board.common.event.payload.CommentCreatedEventPayload;
import msa.board.common.event.payload.CommentDeletedEventPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {

	ARTICLE_CREATED(ArticleCreatedEventPayload.class, Topic.JWJUNG_BOARD_ARTICLE),
	ARTICLE_UPDATED(ArticleUpdatedEventPayload.class, Topic.JWJUNG_BOARD_ARTICLE),
	ARTICLE_DELETED(ArticleDeletedEventPayload.class, Topic.JWJUNG_BOARD_ARTICLE),
	COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.JWJUNG_BOARD_COMMENT),
	COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.JWJUNG_BOARD_COMMENT),
	ARTICLE_LIKED(ArticleLikedEventPayload.class, Topic.JWJUNG_BOARD_LIKE),
	ARTICLE_UNLIKED(ArticleUnlikedEventPayload.class, Topic.JWJUNG_BOARD_LIKE),
	ARTICLE_VIEWED(ArticleViewedEventPayload.class, Topic.JWJUNG_BOARD_VIEW);

	private final Class<? extends EventPayload> payloadClass;
	private final String topic;

	public static EventType from(String type) {
		try {
			return valueOf(type);
		} catch (Exception e) {
			log.error("[EventType.from] type={}", type, e);
			return null;
		}
	}

	public static class Topic {
		public static final String JWJUNG_BOARD_ARTICLE = "jwjung-board_article";
		public static final String JWJUNG_BOARD_COMMENT = "jwjung-board-comment";
		public static final String JWJUNG_BOARD_LIKE = "jwjung-board-like";
		public static final String JWJUNG_BOARD_VIEW = "jwjung-board-view";
	}

}
