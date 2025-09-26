package msa.board.comment.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import msa.board.comment.service.CommentService;
import msa.board.comment.service.request.CommentCreateRequest;
import msa.board.comment.service.response.CommentResponse;

@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/v1/comments/{commentId}")
	public CommentResponse read(
			@PathVariable("commentId") Long commentId
	) {
		return commentService.read(commentId);
	}

	@PostMapping("/v1/comments")
	public CommentResponse create(@RequestBody CommentCreateRequest request) {
		return commentService.create(request);
	}

	@DeleteMapping("/v1/comments/{commentId}")
	public void delte(@PathVariable("commentId") Long commentId) {
		commentService.delete(commentId);
	}
}
