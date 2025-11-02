package msa.board.articleread.service.event.handler;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import msa.board.articleread.repository.ArticleQueryModelRepository;
import msa.board.common.event.Event;
import msa.board.common.event.EventType;
import msa.board.common.event.payload.ArticleDeletedEventPayload;
import msa.board.common.event.payload.ArticleUpdatedEventPayload;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {

	private final ArticleQueryModelRepository articleQueryModelRepository;

	@Override
	public boolean supports(Event<ArticleDeletedEventPayload> event) {
		return EventType.ARTICLE_DELETED == event.getType();
	}

	@Override
	public void handle(Event<ArticleDeletedEventPayload> event) {
		ArticleDeletedEventPayload payload = event.getPayload();
		articleQueryModelRepository.delete(payload.getArticleId());
	}
}
