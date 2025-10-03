package msa.board.comment.api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import msa.board.comment.service.response.CommentPageResponse;
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

	@Test
	void readAll() {
		CommentPageResponse response = restClient.get()
				.uri("/v1/comments?articleId=1&page=1&pageSize=10")
				.retrieve()
				.body(CommentPageResponse.class);

		System.out.println("response.getCommentCount() = " + response.getCommentCount());

		for (CommentResponse comment : response.getComments()) {
			if (!comment.getCommentId().equals(comment.getParentCommentId())) {
				System.out.print("\t");
			}
			System.out.println("comment.getCommentId() = " + comment.getCommentId());
		}
	}

	@Test
	void readAllInfiniteScroll() {
		List<CommentResponse> response1 = restClient.get()
				.uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5")
				.retrieve()
				.body(new ParameterizedTypeReference<List<CommentResponse>>() {
				});

		System.out.println("firstPage");

		for (CommentResponse comment : response1) {
			if (!comment.getCommentId().equals(comment.getParentCommentId())) {
				System.out.print("\t");
			}
			System.out.println("comment.getCommentId() = " + comment.getCommentId());
		}

		Long lastCommentId = response1.getLast().getCommentId();
		Long lastParentCommentId = response1.getLast().getParentCommentId();

		List<CommentResponse> response2 = restClient.get()
				.uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5&lastCommentId=%s&lastParentCommentId=%s".formatted(
						lastCommentId, lastParentCommentId
				))
				.retrieve()
				.body(new ParameterizedTypeReference<List<CommentResponse>>() {
				});

		System.out.println("secondPage");

		for (CommentResponse comment : response2) {
			if (!comment.getCommentId().equals(comment.getParentCommentId())) {
				System.out.print("\t");
			}
			System.out.println("comment.getCommentId() = " + comment.getCommentId());
		}
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
