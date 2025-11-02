package msa.board.articleread.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeClient {

	private RestClient restClient;

	@Value("${endpoints.jwjung-board-comment-service.url}")
	private String commentServiceUrl;

	@PostConstruct
	public void initRestClient() {
		restClient = RestClient.create(commentServiceUrl);
	}

	public long count(Long articleId) {
		try {
			return restClient.get()
					.uri("/v1/article-likes/articles/{articleId}/count")
					.retrieve()
					.body(Long.class);
		} catch (Exception e) {
			log.error("[LikeClient.read] articleId={}", articleId, e);
			return 0;
		}
	}
}
