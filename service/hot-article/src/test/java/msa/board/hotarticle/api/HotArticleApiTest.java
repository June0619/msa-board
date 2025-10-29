package msa.board.hotarticle.api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import msa.board.hotarticle.service.response.HotArticleResponse;

public class HotArticleApiTest {

	RestClient restClient = RestClient.create("http://localhost:9004");

	@Test
	void readAllTest() {
		List<HotArticleResponse> responses = restClient.get()
				.uri("/v1/hot-articles/articles/date/{dateStr}", "20251029")
				.retrieve()
				.body(new ParameterizedTypeReference<List<HotArticleResponse>>() {
				});

		for (HotArticleResponse response : responses) {
			System.out.println("response = " + response);
		}
	}
}
