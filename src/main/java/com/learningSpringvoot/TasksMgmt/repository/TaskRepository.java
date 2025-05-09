package com.learningSpringvoot.TasksMgmt.repository;

import com.learningSpringvoot.TasksMgmt.model.Tasks;
import com.learningSpringvoot.TasksMgmt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks, Long>, JpaSpecificationExecutor<Tasks> {

    List<Tasks> findByUser(User user);

}
