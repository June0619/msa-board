package msa.board.articleread.api;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import msa.board.articleread.service.response.ArticleReadPageResponse;
import msa.board.articleread.service.response.ArticleReadResponse;

public class ArticleReadApiTest {
	RestClient articleReadRestClient = RestClient.create("http://localhost:9005");
	RestClient articleRestClient = RestClient.create("http://localhost:8999");

	@Test
	void readAllTest() {
		ArticleReadPageResponse response1 = articleReadRestClient.get()
				.uri("/v1/articles?boardId=%s&page=%s&pageSize=%s".formatted(1L, 3000L, 5))
				.retrieve()
				.body(ArticleReadPageResponse.class);

		System.out.println("response1.getArticleCount() = " + response1.getArticleCount());

		for (ArticleReadResponse article : response1.getArticles()) {
			System.out.println("article.getArticleId() = " + article.getArticleId());
		}

		ArticleReadPageResponse response2 = articleRestClient.get()
				.uri("/v1/articles?boardId=%s&page=%s&pageSize=%s".formatted(1L, 3000L, 5))
				.retrieve()
				.body(ArticleReadPageResponse.class);

		System.out.println("response2.getArticleCount() = " + response2.getArticleCount());

		for (ArticleReadResponse article : response2.getArticles()) {
			System.out.println("article.getArticleId() = " + article.getArticleId());
		}
	}
	
	@Test
	void readTest() {
		ArticleReadResponse response = articleReadRestClient.get()
				.uri("/v1/articles/{articleId}", 181034649178660864L)
				.retrieve()
				.body(ArticleReadResponse.class);

		System.out.println("response = " + response);
	}
}
