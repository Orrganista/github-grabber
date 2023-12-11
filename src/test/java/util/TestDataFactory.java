package util;

import com.orrganista.githubgrabber.model.dto.CreateRepositoryDto;
import com.orrganista.githubgrabber.model.dto.UpdateRepositoryDto;
import com.orrganista.githubgrabber.model.entity.Repository;
import com.orrganista.githubgrabber.model.entity.RepositoryId;
import com.orrganista.githubgrabber.remote.github.model.GithubRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDataFactory {

    public static Repository getTestRepository() {
        return Repository.builder()
                .id(new RepositoryId("owner", "repo"))
                .description("This is a sample repository.")
                .cloneUrl("https://github.com/example/sample-repo.git")
                .stars(100)
                .createdAt(OffsetDateTime.now().minusDays(14))
                .build();
    }

    public static GithubRepository getTestGithubRepository() {
        return GithubRepository.builder()
                .fullName("Sample Repository")
                .description("This is a sample github repository.")
                .cloneUrl("https://github.com/example/sample-repo.git")
                .stargazersCount(100)
                .createdAt(OffsetDateTime.now().minusDays(14))
                .build();
    }

    public static List<GithubRepository> getTestGithubRepositories() {
        return List.of(
                GithubRepository.builder()
                        .fullName("Sample Repository 1")
                        .description("This is the first sample github repository.")
                        .cloneUrl("https://github.com/example/sample-repo-1.git")
                        .stargazersCount(50)
                        .createdAt(OffsetDateTime.now())
                        .build(),

                GithubRepository.builder()
                        .fullName("Sample Repository 2")
                        .description("This is the second sample github repository.")
                        .cloneUrl("https://github.com/example/sample-repo-2.git")
                        .stargazersCount(75)
                        .createdAt(OffsetDateTime.now())
                        .build()
        );
    }

    public static CreateRepositoryDto getTestCreateRepositoryDto() {
        return CreateRepositoryDto.builder()
                .description("This is a sample repository.")
                .cloneUrl("https://github.com/example/sample-repo.git")
                .stars(100)
                .createdAt(OffsetDateTime.now().minusDays(14))
                .build();
    }

    public static UpdateRepositoryDto getTestUpdateRepositoryDto() {
        return UpdateRepositoryDto.builder()
                .repositoryName("updated-repo")
                .description("This is a updated sample repository.")
                .cloneUrl("https://github.com/example/updated/sample-repo.git")
                .stars(200)
                .build();
    }
}
