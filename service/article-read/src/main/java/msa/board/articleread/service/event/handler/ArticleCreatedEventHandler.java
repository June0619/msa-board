package msa.board.articleread.service.event.handler;

import java.time.Duration;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import msa.board.articleread.repository.ArticleQueryModel;
import msa.board.articleread.repository.ArticleQueryModelRepository;
import msa.board.common.event.Event;
import msa.board.common.event.EventType;
import msa.board.common.event.payload.ArticleCreatedEventPayload;

@Component
@RequiredArgsConstructor
public class ArticleCreatedEventHandler implements EventHandler<ArticleCreatedEventPayload> {

	private final ArticleQueryModelRepository articleQueryModelRepository;

	@Override
	public boolean supports(Event<ArticleCreatedEventPayload> event) {
		return EventType.ARTICLE_CREATED == event.getType();
	}

	@Override
	public void handle(Event<ArticleCreatedEventPayload> event) {
		ArticleCreatedEventPayload payload = event.getPayload();
		articleQueryModelRepository.create(
				ArticleQueryModel.create(payload),
				Duration.ofDays(1)
		);
	}
}
