package msa.board.articleread.service.event.handler;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import msa.board.articleread.repository.ArticleQueryModelRepository;
import msa.board.common.event.Event;
import msa.board.common.event.EventType;
import msa.board.common.event.payload.ArticleUpdatedEventPayload;

@Component
@RequiredArgsConstructor
public class ArticleUpdatedEventHandler implements EventHandler<ArticleUpdatedEventPayload> {

	private final ArticleQueryModelRepository articleQueryModelRepository;

	@Override
	public boolean supports(Event<ArticleUpdatedEventPayload> event) {
		return EventType.ARTICLE_UPDATED == event.getType();
	}

	@Override
	public void handle(Event<ArticleUpdatedEventPayload> event) {
		articleQueryModelRepository.read(event.getPayload().getArticleId())
				.ifPresent(articleQueryModel -> {
					articleQueryModel.updateBy(event.getPayload());
					articleQueryModelRepository.update(articleQueryModel);
				});
	}
}
