package com.example.recuritmenttaskv01.unitTest;


import com.example.recruitmenttaskv01.WebClient.GithubApiConnection;
import com.example.recruitmenttaskv01.model.dto.RepositoryDTO;
import com.example.recruitmenttaskv01.model.githubRepositoryModel.Owner;
import com.example.recruitmenttaskv01.model.githubRepositoryModel.Repository;
import com.example.recruitmenttaskv01.model.githubRepositoryModel.branch.Branch;
import com.example.recruitmenttaskv01.model.githubRepositoryModel.branch.Commit;
import com.example.recruitmenttaskv01.service.RepositoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RepositoryServiceTest {
    private AutoCloseable closeable;

    @Mock
    private GithubApiConnection githubApiConnection;

    @InjectMocks
    private RepositoryService repositoryService;

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void destroy() throws Exception {
        closeable.close();
    }

    @Test
    void testGetRepositories() {
        String username = "testUser";
        Owner owner = new Owner("login");
        Repository repository = new Repository("repo1", false, owner, List.of());
        List<Repository> repositories = List.of(repository);

        when(githubApiConnection.getRepositoriesForUser(username)).thenReturn(Flux.fromIterable(repositories));
        when(githubApiConnection.getBranchesForRepository(anyString(), anyString())).thenReturn(Flux.empty());

        Flux<RepositoryDTO> result = repositoryService.getRepositoriesWithBranches(username);

        StepVerifier.create(result)
                .expectNextMatches(repoDTO -> repoDTO.repositoryName().equals("repo1") && repoDTO.branchList().isEmpty())
                .verifyComplete();
    }

    @Test
    void testAssignBranchesToRepositories() {
        String username = "testUser";
        Owner owner = new Owner("login");
        Repository repository = new Repository("repo1", false, owner, List.of());
        List<Repository> repositories = List.of(repository);

        Branch branch = new Branch("Name", new Commit("commitSha"));
        List<Branch> branches = List.of(branch);

        when(githubApiConnection.getRepositoriesForUser(username)).thenReturn(Flux.fromIterable(repositories));
        when(githubApiConnection.getBranchesForRepository(anyString(), anyString())).thenReturn(Flux.fromIterable(branches));

        Flux<RepositoryDTO> result = repositoryService.getRepositoriesWithBranches(username);

        StepVerifier.create(result)
                .expectNextMatches(repoDTO -> repoDTO.repositoryName().equals("repo1") && repoDTO.branchList().size() == 1)
                .verifyComplete();
    }

    @Test
    void testAssignBranchesToRepository() {
        String username = "testUser";
        Owner owner = new Owner("login");
        Repository repository = new Repository("testRepo", false, owner, List.of());

        Branch branch1 = new Branch("main", new Commit("commitSha1"));
        Branch branch2 = new Branch("dev", new Commit("commitSha2"));
        List<Branch> branches = List.of(branch1, branch2);

        when(githubApiConnection.getRepositoriesForUser(username)).thenReturn(Flux.just(repository));
        when(githubApiConnection.getBranchesForRepository(username, "testRepo")).thenReturn(Flux.fromIterable(branches));

        Flux<RepositoryDTO> result = repositoryService.getRepositoriesWithBranches(username);

        StepVerifier.create(result)
                .expectNextMatches(repoDTO -> repoDTO.repositoryName().equals("testRepo") && repoDTO.branchList().size() == 2)
                .verifyComplete();
    }
}
