package msa.board.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import msa.board.article.entity.Article;
import msa.board.article.repository.ArticleRepository;
import msa.board.article.service.request.ArticleCreateRequest;
import msa.board.article.service.request.ArticleUpdateRequest;
import msa.board.article.service.response.ArticleResponse;

@Service
@RequiredArgsConstructor
public class ArticleService {

	private final Snowflake snowflake = new Snowflake();
	private final ArticleRepository articleRepository;

	@Transactional
	public ArticleResponse create(ArticleCreateRequest request) {
		Article article = articleRepository.save(
				Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(),
						request.getWriterId())
		);

		return ArticleResponse.from(article);
	}

	@Transactional
	public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
		Article article = articleRepository.findById(articleId).orElseThrow();
		article.update(request.getTitle(), request.getContent());
		return ArticleResponse.from(article);
	}

	public ArticleResponse read(Long articleId) {
		return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
	}

	@Transactional
	public void delete(Long articleId) {
		articleRepository.deleteById(articleId);
	}

}
