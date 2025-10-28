package msa.board.hotarticle.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import msa.board.hotarticle.service.HotArticleService;
import msa.board.hotarticle.service.response.HotArticleResponse;

@RestController
@RequiredArgsConstructor
public class HotArticleController {

	private final HotArticleService hotArticleService;

	@GetMapping("/v1/hot-articles/articles/date/{dateStr}")
	public List<HotArticleResponse> readAll(
			@PathVariable("dateStr") String dateStr
	) {
		return hotArticleService.readAll(dateStr);
	}
}
