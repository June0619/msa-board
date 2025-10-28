package msa.board.hotarticle.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import kuke.board.common.event.Event;
import kuke.board.common.event.EventPayload;
import kuke.board.common.event.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.board.hotarticle.service.HotArticleService;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotArticleEventConsumer {
	private final HotArticleService hotArticleService;

	@KafkaListener(topics = {
			EventType.Topic.JWJUNG_BOARD_ARTICLE,
			EventType.Topic.JWJUNG_BOARD_COMMENT,
			EventType.Topic.JWJUNG_BOARD_LIKE,
			EventType.Topic.JWJUNG_BOARD_VIEW
	})
	public void listen(String message, Acknowledgment ack) {
		log.info("[HotArticleEventConsumer.listen] received message={}", message);
		Event<EventPayload> event = Event.fromJson(message);
		if (event != null) {
			hotArticleService.handleEvent(event);
		}
		ack.acknowledge();
	}
}
