package msa.board.articleread.cache;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OptimizedCacheTtlTest {

	@Test
	void ofTest() {
		// given
		long ttlSeconds = 10;

		// when
		OptimizedCacheTtl optimizedCacheTtl = OptimizedCacheTtl.of(ttlSeconds);

		// then
		assertThat(optimizedCacheTtl.getLogicalTtl()).isEqualTo(Duration.ofSeconds(ttlSeconds));
		assertThat(optimizedCacheTtl.getPhysicalTtl()).isEqualTo(
				Duration.ofSeconds(ttlSeconds).plusSeconds(OptimizedCacheTtl.PHYSICAL_TTL_DELAY_SECONDS
		));
	}

}