package msa.board.article.api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import msa.board.article.service.response.ArticlePageResponse;
import msa.board.article.service.response.ArticleResponse;

public class ArticleApiTest {

	RestClient restClient = RestClient.create("http://localhost:9000");

	@Test
	void createTest() {
		ArticleResponse response = create(new ArticleCreateRequest(
				"hi", "my content", 1L, 1L
		));

		System.out.println("response = " + response);
	}

	ArticleResponse create(ArticleCreateRequest request) {
		return restClient.post()
				.uri("/v1/articles")
				.body(request)
				.retrieve()
				.body(ArticleResponse.class);
	}

	@Test
	void readTest() {
		ArticleResponse response = read(181030142737920000L);
		System.out.println("response = " + response);
	}

	@Test
	void updateTest() {
		Long articleId = 181030142737920000L;

		restClient.put()
				.uri("/v1/articles/{articleId}", articleId)
				.body(new ArticleUpdateRequest("hi2", "my content22"))
				.retrieve();

		ArticleResponse response = read(articleId);
		System.out.println("response = " + response);
	}

	@Test
	void deleteTest() {
		Long articleId = 181030142737920000L;
		restClient.delete()
				.uri("/v1/articles/{articleId}", articleId)
				.retrieve();
	}

	ArticleResponse read(Long articleId) {
		return restClient.get()
				.uri("/v1/articles/{articleId}", articleId)
				.retrieve()
				.body(ArticleResponse.class);
	}


	@Getter
	@AllArgsConstructor
	static class ArticleCreateRequest {
		private String title;
		private String content;
		private Long writerId;
		private Long boardId;
	}

	@Getter
	@AllArgsConstructor
	static class ArticleUpdateRequest {
		private String title;
		private String content;
	}
	
	@Test
	void readAllTest() {
		ArticlePageResponse response = restClient.get()
				.uri("/v1/articles?boardId=1&pageSize=30&page=50000")
				.retrieve()
				.body(ArticlePageResponse.class);

		System.out.println("response.getArticleCount() = " + response.getArticleCount());

		for (ArticleResponse article : response.getArticles()) {
			System.out.println("article.getArticleId() = " + article.getArticleId());
		}
	}

	@Test
	void readAllInfiniteScrollTest() {
		List<ArticleResponse> articles1 = restClient.get()
				.uri("v1/articles/infinite-scroll?boardId=1&pageSize=5")
				.retrieve()
				.body(new ParameterizedTypeReference<List<ArticleResponse>>() {
				});

		System.out.println("first page");

		for (ArticleResponse response : articles1) {
			System.out.println("response.getArticleId() = " + response.getArticleId());
		}

		Long lastArticleId = articles1.getLast().getArticleId();

		List<ArticleResponse> articles2 = restClient.get()
				.uri("v1/articles/infinite-scroll?boardId=1&pageSize=5&lastArticleId=%s".formatted(lastArticleId))
				.retrieve()
				.body(new ParameterizedTypeReference<List<ArticleResponse>>() {
				});

		System.out.println("second page");

		for (ArticleResponse response : articles2) {
			System.out.println("response.getArticleId() = " + response.getArticleId());
		}
	}
}
