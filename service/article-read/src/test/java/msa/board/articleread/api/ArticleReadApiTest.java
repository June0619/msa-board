package msa.board.articleread.api;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import msa.board.articleread.service.response.ArticleReadResponse;

public class ArticleReadApiTest {
	RestClient restClient = RestClient.create("http://localhost:9005");

	@Test
	void readTest() {
		ArticleReadResponse response = restClient.get()
				.uri("/v1/articles/{articleId}", 181034649178660864L)
				.retrieve()
				.body(ArticleReadResponse.class);

		System.out.println("response = " + response);
	}
}
