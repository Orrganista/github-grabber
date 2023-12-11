package com.orrganista.githubgrabber.controller;

import com.orrganista.githubgrabber.model.dto.CreateRepositoryDto;
import com.orrganista.githubgrabber.model.dto.RepositoryResponseDto;
import com.orrganista.githubgrabber.model.dto.UpdateRepositoryDto;
import com.orrganista.githubgrabber.service.GithubService;
import com.orrganista.githubgrabber.service.RepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repositories/{owner}/{repository-name}")
@RequiredArgsConstructor
public class RepositoryController {

    private final RepositoryService repositoryService;


    @Operation(summary = "Create a new repository for the specified user and save it to the local database.",
            description = "Creates a new repository with the given name for the specified user and saves it to the local database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repository created successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data.", content = @Content)
    })
    @PostMapping()
    public RepositoryResponseDto createUserRepository(@Parameter(description = "name of user") @PathVariable String owner,
                                                      @Parameter(description = "name of repository") @PathVariable("repository-name") String repo,
                                                      @Parameter(description = "new repository") @RequestBody CreateRepositoryDto createRepositoryDto) {
        return repositoryService.createUserRepository(owner, repo, createRepositoryDto);
    }

    @Operation(summary = "Update an existing repository for the specified user by its name.",
            description = "Updates the information of the specified repository for the specified user in the local database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repository updated successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Repository not found.", content = @Content)
    })
    @PutMapping()
    public RepositoryResponseDto putUserRepositoryByName(@Parameter(description = "name of user") @PathVariable String owner,
                                                         @Parameter(description = "name of repository") @PathVariable("repository-name") String repo,
                                                         @Parameter(description = "updated repository") @RequestBody UpdateRepositoryDto updateRepositoryDto) {
        return repositoryService.putUserRepositoryByName(owner, repo, updateRepositoryDto);
    }

    @Operation(summary = "Delete an existing repository for the specified user by its name.",
            description = "Deletes the specified repository for the specified user from the local database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Repository deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Repository not found.")
    })
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocalUserRepositoryByName(@PathVariable String owner, @PathVariable("repository-name") String repo) {
        repositoryService.deleteLocalUserRepositoryByName(owner, repo);
    }
}
