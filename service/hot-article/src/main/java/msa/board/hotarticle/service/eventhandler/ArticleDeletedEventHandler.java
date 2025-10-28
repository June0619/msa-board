package msa.board.hotarticle.service.eventhandler;

import org.springframework.stereotype.Component;

import msa.board.common.event.Event;
import msa.board.common.event.EventType;
import msa.board.common.event.payload.ArticleDeletedEventPayload;
import lombok.RequiredArgsConstructor;
import msa.board.hotarticle.repository.ArticleCreatedTimeRepository;
import msa.board.hotarticle.repository.HotArticleListRepository;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {
	private final HotArticleListRepository hotArticleListRepository;
	private final ArticleCreatedTimeRepository articleCreatedTimeRepository;

	@Override
	public Long findArticleId(Event<ArticleDeletedEventPayload> event) {
		return event.getPayload().getArticleId();
	}

	@Override
	public boolean supports(Event<ArticleDeletedEventPayload> event) {
		return event.getType() == EventType.ARTICLE_DELETED;
	}

	@Override
	public void handle(Event<ArticleDeletedEventPayload> event) {
		ArticleDeletedEventPayload payload = event.getPayload();
		articleCreatedTimeRepository.delete(payload.getArticleId());
		hotArticleListRepository.remove(payload.getArticleId(), payload.getCreatedAt());
	}
}
