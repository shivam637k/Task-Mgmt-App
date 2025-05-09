package com.learningSpringvoot.TasksMgmt.specification;

import com.learningSpringvoot.TasksMgmt.model.TaskStatus;
import com.learningSpringvoot.TasksMgmt.model.Tasks;
import com.learningSpringvoot.TasksMgmt.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecification {
    public static Specification<Tasks> hasTitle(String title) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("taskTitle")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Tasks> hasDescription(String description) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("taskDescription")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Tasks> hasStatus(TaskStatus status) {
        return (root, query, builder) -> {
            if (status == null) return null;
            return builder.equal(root.get("taskStatus"), status);
        };
    }

    public static Specification<Tasks> hasDueDate(LocalDate dueDate) {
        return (root, query, builder) ->
                builder.equal(root.get("dueDate"), dueDate);
    }

    public static Specification<Tasks> hasUser(User user) {
        return (root, query, builder) ->
                builder.equal(root.get("user"), user);
    }
}
