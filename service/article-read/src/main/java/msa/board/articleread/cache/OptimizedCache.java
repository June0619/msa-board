package msa.board.articleread.cache;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.ToString;
import msa.board.common.dataserializer.DataSerializer;

@Getter
@ToString
public class OptimizedCache {

	private String data;
	private LocalDateTime expiredAt;

	public static OptimizedCache of(Object data, Duration ttl) {
		OptimizedCache optimizedCache = new OptimizedCache();
		optimizedCache.data = DataSerializer.serialize(data);
		optimizedCache.expiredAt = LocalDateTime.now().plus(ttl);
		return optimizedCache;
	}

	@JsonIgnore
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expiredAt);
	}

	public <T> T parseData(Class<T> dataType) {
		return DataSerializer.deserialize(data, dataType);
	}
}
