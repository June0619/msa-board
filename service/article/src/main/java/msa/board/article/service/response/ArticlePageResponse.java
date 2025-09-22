package msa.board.article.service.response;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticlePageResponse {
	private List<ArticleResponse> articles;
	private Long articleCount;

	private ArticlePageResponse(final List<ArticleResponse> articles, final Long articleCount) {
		this.articles = articles;
		this.articleCount = articleCount;
	}

	public static ArticlePageResponse of(List<ArticleResponse> articles, Long articleCount) {
		return new ArticlePageResponse(articles, articleCount);
	}
}
