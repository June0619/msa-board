package msa.board.comment.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import msa.board.comment.service.response.CommentResponse;

public class CommentApiTest {

	RestClient restClient = RestClient.create("http://localhost:9001");

	@Test
	void create() {
		CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
		CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
		CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));

		System.out.println("commentId=%s".formatted(response1.getCommentId()));
		System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
		System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));
	}

	CommentResponse createComment(CommentCreateRequest request) {
		return restClient.post()
				.uri("/v1/comments")
				.body(request)
				.retrieve()
				.body(CommentResponse.class);
	}

	@Test
	void read() {
		CommentResponse response = restClient.get()
				.uri("/v1/comments/{commentId}", 230899846358089728L)
				.retrieve()
				.body(CommentResponse.class);

		System.out.println("response = " + response);
	}

	@Test
	void delete() {
		//commentId=230899846358089728
		// 	commentId=230899846748160000
		// 	commentId=230899846806880256

		restClient.delete()
				.uri("/v1/comments/{commentId}", 230899846806880256L)
				.retrieve();
	}



	@Getter
	@AllArgsConstructor
	public static class CommentCreateRequest {
		private Long articleId;
		private String content;
		private Long parentCommentId;
		private Long writerId;
	}

}
