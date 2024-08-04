package com.example.recruitmenttaskv01.controller;

import com.example.recruitmenttaskv01.model.dto.RepositoryDTO;
import com.example.recruitmenttaskv01.service.RepositoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class RepositoryController {
    RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(value = "/repositories/{username}")
    public ResponseEntity<Flux<RepositoryDTO>> getRepositories(
            @PathVariable String username,
            @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader
    ) {
            return ResponseEntity.ok(repositoryService.getRepositoriesWithBranches(username));
    }
}