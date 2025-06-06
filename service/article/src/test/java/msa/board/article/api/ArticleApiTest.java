package msa.board.article.api;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
}
