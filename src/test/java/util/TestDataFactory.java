package util;

import com.orrganista.githubgrabber.remote.githubapi.model.Repository;

import java.time.LocalDateTime;
import java.util.List;

public class TestDataFactory {

    public static Repository getTestRepository() {
        return Repository.builder()
                .fullName("Sample Repository")
                .description("This is a sample repository.")
                .cloneUrl("https://github.com/example/sample-repo.git")
                .stargazersCount(100)
                .createdAt(LocalDateTime.now().minusDays(14))
                .build();
    }

    public static List<Repository> getTestRepositoryList() {
        return List.of(
                Repository.builder()
                        .fullName("Sample Repository 1")
                        .description("This is the first sample repository.")
                        .cloneUrl("https://github.com/example/sample-repo-1.git")
                        .stargazersCount(50)
                        .createdAt(LocalDateTime.now())
                        .build(),

                Repository.builder()
                        .fullName("Sample Repository 2")
                        .description("This is the second sample repository.")
                        .cloneUrl("https://github.com/example/sample-repo-2.git")
                        .stargazersCount(75)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }
}
