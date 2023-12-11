package com.orrganista.githubgrabber.controller;

import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.service.RepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/local")
@RequiredArgsConstructor
public class LocalController {

    private final RepositoryService repositoryService;

    @Operation(summary = "Get a user's repository by its name from local database.", description = "Retrieves information about the specified repository for the specified user local database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found repository.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Repository not found.", content = @Content)}
    )
    @GetMapping("/repositories/{owner}/{repository-name}")
    public RepositoryResponseDto getLocalUserRepositoryByName(@Parameter(description = "name of user") @PathVariable String owner,
                                                              @Parameter(description = "name of repository") @PathVariable("repository-name") String repo) {
        return repositoryService.getUserRepositoryByName(owner, repo);
    }
}
