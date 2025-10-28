package msa.board.hotarticle.service.eventhandler;

import org.springframework.stereotype.Component;

import msa.board.common.event.Event;
import msa.board.common.event.EventType;
import msa.board.common.event.payload.ArticleViewedEventPayload;
import lombok.RequiredArgsConstructor;
import msa.board.hotarticle.repository.ArticleViewCountRepository;
import msa.board.hotarticle.utils.TimeCalculatorUtils;

@Component
@RequiredArgsConstructor
public class ArticleViewedEventHandler implements EventHandler<ArticleViewedEventPayload> {
	private final ArticleViewCountRepository articleViewCountRepository;

	@Override
	public Long findArticleId(Event<ArticleViewedEventPayload> event) {
		return event.getPayload().getArticleId();
	}

	@Override
	public boolean supports(Event<ArticleViewedEventPayload> event) {
		return EventType.ARTICLE_VIEWED == event.getType();
	}

	@Override
	public void handle(Event<ArticleViewedEventPayload> event) {
		ArticleViewedEventPayload payload = event.getPayload();
		articleViewCountRepository.createOrUpdate(
				payload.getArticleId(),
				payload.getArticleCountView(),
				TimeCalculatorUtils.calculateDurationToMidnight()
		);
	}
}
