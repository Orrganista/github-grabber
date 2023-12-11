package com.orrganista.githubgrabber.controller;

import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.service.GithubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @Operation(summary = "Get all user repositories.", description = "Retrieves a list of all user repositories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user repositories.",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RepositoryResponseDto.class)))}),
            @ApiResponse(responseCode = "301", description = "Moved permanently.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Data forbidden.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Data not found.", content = @Content)}
    )
    @GetMapping("/github-details/{owner}")
    public List<RepositoryResponseDto> getUserRepositories(@Parameter(description = "name of repository owner") @PathVariable String owner) {
        return githubService.getUserRepositories(owner);
    }

    @Operation(summary = "Get a user's repository by its name.", description = "Retrieves information about the specified repository for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found repository.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "301", description = "Moved permanently.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Data forbidden.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Data not found.", content = @Content)}
    )
    @GetMapping("/repositories/{owner}/{repository-name}")
    public RepositoryResponseDto getUserRepositoryByName(@Parameter(description = "name of user") @PathVariable String owner,
                                                         @Parameter(description = "name of repository") @PathVariable("repository-name") String repo) {
        return githubService.getUserRepositoryByName(owner, repo);
    }
}
