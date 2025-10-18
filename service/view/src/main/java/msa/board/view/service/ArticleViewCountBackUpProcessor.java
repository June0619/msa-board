package msa.board.view.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import msa.board.view.entity.ArticleViewCount;
import msa.board.view.repository.ArticleViewCountBackUpRepository;

@Component
@RequiredArgsConstructor
public class ArticleViewCountBackUpProcessor {
	private final ArticleViewCountBackUpRepository repository;
	private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;

	@Transactional
	public void backUp(Long articleId, Long viewCount) {
		int result = repository.updateViewCount(articleId, viewCount);
		if (result == 0) {
			repository.findById(articleId)
					.ifPresentOrElse(ignored -> {},
							() -> articleViewCountBackUpRepository.save(ArticleViewCount.init(articleId, viewCount))
					);
		}
	}
}
