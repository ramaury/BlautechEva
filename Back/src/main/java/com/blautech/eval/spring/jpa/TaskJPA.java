package com.blautech.eval.spring.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.blautech.eval.spring.model.Task;

public interface TaskJPA extends JpaRepository<Task, Long> {
	List<Task> findByUser(long user);

	@Transactional
	@Modifying
	@Query("delete from Task b where b.user=:user")
	int deleteTasks(@Param("user") long user);
}
