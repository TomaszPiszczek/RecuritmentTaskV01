package com.example.recruitmenttaskv01.WebClient;


import com.example.recruitmenttaskv01.exception.UserNotFoundException;
import com.example.recruitmenttaskv01.model.githubRepositoryModel.Repository;
import com.example.recruitmenttaskv01.model.githubRepositoryModel.branch.Branch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubApiConnection {

    private final WebClient githubWebClient;

    public Flux<Repository> getRepositoriesForUser(String username) {
        String url = String.format("/users/%s/repos", username);
        return githubWebClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND),
                        clientResponse -> Mono.error(new UserNotFoundException("User not found")))
                .bodyToFlux(Repository.class)
                .filter(repository -> !repository.isFork());
    }

    public Flux<Branch> getBranchesForRepository(String username, String repositoryName) {
        String url = String.format("/repos/%s/%s/branches", username, repositoryName);
        return githubWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Branch.class);
    }
}
