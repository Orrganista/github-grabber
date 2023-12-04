package com.orrganista.githubgrabber.controller;

import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.service.GithubApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github-details")
@RequiredArgsConstructor
public class GithubApiController {

    private final GithubApiService githubApiService;

    @Operation(summary = "Get all user repositories.", description = "Retrieves a list of all user repositories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user repositories.",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RepositoryResponseDto.class)))}),
            @ApiResponse(responseCode = "301", description = "Moved permanently.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Data forbidden.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Data not found.", content = @Content)}
    )
    @GetMapping("/{owner}")
    public List<RepositoryResponseDto> getUserRepositories(@Parameter(description = "name of repository owner") @PathVariable String owner) {
        return githubApiService.getUserRepositories(owner);
    }

    @Operation(summary = "Get a user's repository by its name.", description = "Retrieves information about the specified repository for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found repository.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "301", description = "Moved permanently.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Data forbidden.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Data not found.", content = @Content)}
    )
    @GetMapping("/{owner}/{repo}")
    public RepositoryResponseDto getUserRepositoryByName(@Parameter(description = "name of user") @PathVariable String owner,
                                                         @Parameter(description = "name of repository") @PathVariable String repo) {
        return githubApiService.getUserRepositoryByName(owner, repo);
    }
}
