package com.atomicjar.todos.web;

import com.atomicjar.todos.entity.Todo;
import com.atomicjar.todos.repository.TodoRepository;
import com.atomicjar.todos.service.Estimation;
import com.atomicjar.todos.service.TodoEstimator;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoEstimator estimator;
    private final TodoEstimator ragged;
    private final TodoRepository repository;
    private final EmbeddingStoreIngestor ingestor;

    public TodoController(TodoRepository repository,
                          @Qualifier("estimator") TodoEstimator estimator,
                          @Qualifier("ragged") TodoEstimator ragged,
                          EmbeddingStoreIngestor ingestor) {
        this.estimator = estimator;
        this.repository = repository;
        this.ragged = ragged;
        this.ingestor = ingestor;
    }

    @GetMapping
    public Iterable<Todo> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Todo> save(@Valid @RequestBody Todo todo) {
        todo.setId(null);
        Estimation estimate = estimator.chat("todo title is '" + todo.getTitle() + "'.");
        System.out.printf("todo: %s has an estimation of %s because: %s%n", todo.getTitle(), estimate.hours, estimate.reason);
        Estimation estimate2 = ragged.chat("todo title is '" + todo.getTitle() + "'.");
        System.out.printf("todo: %s has an estimation of %s because: %s%n", todo.getTitle(), estimate2.hours, estimate2.reason);
        todo.setEstimate("" + estimate.hours);
        Todo savedTodo = repository.save(todo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", savedTodo.getUrl())
                .body(savedTodo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable String id, @Valid @RequestBody Todo todo) {
        Todo existingTodo = repository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        if(todo.getCompleted() != null) {
            existingTodo.setCompleted(todo.getCompleted());
            if (todo.getCompleted()) {
                Document document = new Document(existingTodo.getTitle(), Metadata.metadata("real duration", Duration.between(existingTodo.getCreatedAt().toInstant(), Instant.now()).toHours()));
                ingestor.ingest(document);
            }
        }
        if(todo.getOrder() != null) {
            existingTodo.setOrder(todo.getOrder());
        }
        if(todo.getTitle() != null) {
            existingTodo.setTitle(todo.getTitle());
        }
        Todo updatedTodo = repository.save(existingTodo);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        Todo todo = repository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        repository.delete(todo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        repository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
