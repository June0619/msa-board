package msa.board.common.outboxmessagerelay;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import msa.board.common.event.Event;
import msa.board.common.event.EventPayload;
import msa.board.common.event.EventType;
import msa.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
	private final Snowflake outboxIdSnowflake = new Snowflake();
	private final Snowflake eventIdSnowflake = new Snowflake();
	private final ApplicationEventPublisher applicationEventPublisher;

	public void publish(EventType type, EventPayload payload, Long shardKey) {
		// articleId=10, shardKey == articleId
		// 10 % 4 = (물리적샤드) 2
		Outbox outbox = Outbox.create(
				outboxIdSnowflake.nextId(),
				type,
				Event.of(
						eventIdSnowflake.nextId(), type, payload
				).toJson(),
				shardKey % MessageRelayConstants.SHARD_COUNT
		);

		applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
	}
}
