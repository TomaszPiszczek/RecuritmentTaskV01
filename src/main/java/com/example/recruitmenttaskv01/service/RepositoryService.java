package com.example.recruitmenttaskv01.service;

import com.example.recruitmenttaskv01.WebClient.GithubApiConnection;
import com.example.recruitmenttaskv01.mapper.RepositoryMapper;
import com.example.recruitmenttaskv01.model.dto.RepositoryDTO;
import com.example.recruitmenttaskv01.model.githubRepositoryModel.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RepositoryService {
    private final GithubApiConnection githubApiConnection;

    public RepositoryService(GithubApiConnection githubApiConnection) {
        this.githubApiConnection = githubApiConnection;
    }

    public Flux<RepositoryDTO> getRepositoriesWithBranches(String username) {
        if (username == null) {
            return Flux.error(new NullPointerException("Username is null"));
        }

        return githubApiConnection.getRepositoriesForUser(username)
                .flatMap(repository -> assignBranchesToRepository(repository, username)
                        .map(RepositoryMapper::mapToRepositoryDTO));
    }

    private Mono<Repository> assignBranchesToRepository(Repository repository, String username) {
        return githubApiConnection.getBranchesForRepository(username, repository.getName())
                .collectList()
                .map(branches -> {
                    repository.setBranchList(branches);
                    return repository;
                });
    }
}
