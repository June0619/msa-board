package msa.board.view.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import msa.board.view.repository.ArticleViewCountRepository;
import msa.board.view.repository.ArticleViewDistributedLockRepository;

@Service
@RequiredArgsConstructor
public class ArticleViewService {
	private final ArticleViewCountRepository articleViewCountRepository;
	private final ArticleViewCountBackUpProcessor backUpProcessor;
	private final ArticleViewDistributedLockRepository distributedLockRepository;

	private static final int BACK_UP_BATCH_SIZE = 100;
	private static final Duration TTL = Duration.ofMinutes(10);

	public Long increase(Long articleId, Long userId) {
		if (!distributedLockRepository.lock(articleId, userId, TTL)) {
			return articleViewCountRepository.read(articleId);
		}

		Long count = articleViewCountRepository.increase(articleId);
		if (count % BACK_UP_BATCH_SIZE == 0) {
			backUpProcessor.backUp(articleId, count);
		}
		return count;
	}

	public Long count(Long articleId) {
		return articleViewCountRepository.read(articleId);
	}
}
