package msa.board.hotarticle.service.eventhandler;

import org.springframework.stereotype.Component;

import msa.board.common.event.Event;
import msa.board.common.event.EventType;
import msa.board.common.event.payload.ArticleCreatedEventPayload;
import lombok.RequiredArgsConstructor;
import msa.board.hotarticle.repository.ArticleCreatedTimeRepository;
import msa.board.hotarticle.utils.TimeCalculatorUtils;

@Component
@RequiredArgsConstructor
public class ArticleCreatedEventHandler implements EventHandler<ArticleCreatedEventPayload> {
	private final ArticleCreatedTimeRepository articleCreatedTimeRepository;

	@Override
	public Long findArticleId(Event<ArticleCreatedEventPayload> event) {
		return event.getPayload().getArticleId();
	}

	@Override
	public boolean supports(Event<ArticleCreatedEventPayload> event) {
		return EventType.ARTICLE_CREATED == event.getType();
	}

	@Override
	public void handle(Event<ArticleCreatedEventPayload> event) {
		ArticleCreatedEventPayload payload = event.getPayload();
		articleCreatedTimeRepository.createOrUpdate(
				payload.getArticleId(),
				payload.getCreatedAt(),
				TimeCalculatorUtils.calculateDurationToMidnight()
		);
	}
}
