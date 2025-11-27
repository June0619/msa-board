package msa.board.articleread.cache;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

class OptimizedCacheTest {

	@Test
	void parseDataTest() {
		parseDataTest("data", 10);
		parseDataTest(3L, 10);
		parseDataTest(3, 10);
		parseDataTest(new TestClass("hihi"), 10);
	}

	void parseDataTest(Object data, long ttlSeconds) {
		// given
		OptimizedCache optimizedCache = OptimizedCache.of(data, Duration.ofSeconds(ttlSeconds));
		System.out.println("optimizedCache = " + optimizedCache);

		// when
		Object resolvedData = optimizedCache.parseData(data.getClass());

		// then
		System.out.println("resolvedData = " + resolvedData);
		assertThat(resolvedData).isEqualTo(data);
	}

	@Test
	void isExpiredTest() {
		assertThat(OptimizedCache.of("data", Duration.ofDays(-30)).isExpired()).isTrue();
		assertThat(OptimizedCache.of("data", Duration.ofDays(30)).isExpired()).isFalse();
	}


	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class TestClass {
		String testData;
	}
}