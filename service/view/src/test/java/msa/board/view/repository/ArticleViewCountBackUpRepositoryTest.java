package msa.board.view.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import msa.board.view.entity.ArticleViewCount;

@SpringBootTest
class ArticleViewCountBackUpRepositoryTest {

	@Autowired
	ArticleViewCountBackUpRepository repository;

	@PersistenceContext
	EntityManager em;

	@Test
	@Transactional
	void updateViewCountTest() {
		//given
		repository.save(
				ArticleViewCount.init(1L, 0L)
		);

		em.flush();
		em.clear();

		//순간적으로 트래픽이 몰려 300뷰 요청과 200뷰 요청이 거의 중복으로 왔다고 가정
		int result1 = repository.updateViewCount(1L, 100L);
		int result2 = repository.updateViewCount(1L, 300L);
		int result3 = repository.updateViewCount(1L, 200L);

		assertThat(result1).isEqualTo(1);
		assertThat(result2).isEqualTo(1);
		assertThat(result3).isEqualTo(0);

		ArticleViewCount articleViewCount = repository.findById(1L).get();
		assertThat(articleViewCount.getViewCount()).isEqualTo(300L);
	}

}