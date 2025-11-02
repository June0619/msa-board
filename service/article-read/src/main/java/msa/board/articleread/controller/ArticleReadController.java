package msa.board.articleread.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.board.articleread.service.ArticleReadService;
import msa.board.articleread.service.response.ArticleReadResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleReadController {
	private final ArticleReadService articleReadService;

	@GetMapping("/v1/articles/{articleId}")
	public ArticleReadResponse read(@PathVariable("articleId") Long articleId) {
		return articleReadService.read(articleId);
	}
}
