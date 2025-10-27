package msa.board.hotarticle.service.eventhandler;

import org.springframework.stereotype.Component;

import kuke.board.common.event.Event;
import kuke.board.common.event.EventType;
import kuke.board.common.event.payload.ArticleLikedEventPayload;
import lombok.RequiredArgsConstructor;
import msa.board.hotarticle.repository.ArticleLikeCountRepository;
import msa.board.hotarticle.utils.TimeCalculatorUtils;

@Component
@RequiredArgsConstructor
public class ArticleLikedEventHandler implements EventHandler<ArticleLikedEventPayload> {
	private final ArticleLikeCountRepository articleLikeCountRepository;

	@Override
	public Long findArticleId(Event<ArticleLikedEventPayload> event) {
		return event.getPayload().getArticleId();
	}

	@Override
	public boolean supports(Event<ArticleLikedEventPayload> event) {
		return EventType.ARTICLE_LIKED == event.getType()  ;
	}

	@Override
	public void handle(Event<ArticleLikedEventPayload> event) {
		ArticleLikedEventPayload payload = event.getPayload();
		articleLikeCountRepository.createOrUpdate(
				payload.getArticleId(),
				payload.getArticleLikeCount(),
				TimeCalculatorUtils.calculateDurationToMidnight()
		);
	}
}
