package msa.board.articleread.cache;

import java.time.Duration;

import lombok.Getter;

@Getter
public class OptimizedCacheTtl {

	private Duration logicalTtl;
	private Duration physicalTtl;

	public static final long PHYSICAL_TTL_DELAY_SECONDS = 5;

	public static OptimizedCacheTtl of(long ttlSeconds) {
		OptimizedCacheTtl optimizedCacheTtl = new OptimizedCacheTtl();
		optimizedCacheTtl.logicalTtl = Duration.ofSeconds(ttlSeconds);
		optimizedCacheTtl.physicalTtl = optimizedCacheTtl.logicalTtl.plusSeconds(PHYSICAL_TTL_DELAY_SECONDS);
		return optimizedCacheTtl;
	}

}
