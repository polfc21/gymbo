package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.repositories.FileModelRepository;
import com.behabits.gymbo.domain.services.FileService;
import com.behabits.gymbo.domain.services.TokenService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.FileRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.FileResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.FileApiMapper;
import com.behabits.gymbo.infrastructure.controller.repositories.request.FileRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.FileResponseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @MockBean
    private FileService fileService;

    @MockBean
    private FileApiMapper mapper;

    @MockBean
    private TokenService tokenService; // Added to load application context

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final FileRequest correctFileRequest = new FileRequestRepository().getCorrectFileRequest();

    private final FileRequest incorrectFileRequest = new FileRequestRepository().getIncorrectFileRequest();

    private final File file = new FileModelRepository().getFile();

    private final FileResponse fileResponse = new FileResponseRepository().getFileResponse();

    @Test
    @WithMockUser
    void givenExistentIdWhenFindFileByIdThenReturnFileResponseAnd200() throws Exception {
        Long existentId = 1L;

        given(this.fileService.findFileById(existentId)).willReturn(this.file);
        given(this.mapper.toResponse(this.file)).willReturn(this.fileResponse);

        MockHttpServletResponse response =  this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.FILES + ApiConstant.ID, existentId)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    @WithMockUser
    void givenNonExistentIdWhenFindFileByIdThenReturn404() throws Exception {
        Long nonExistentId = 1L;

        when(this.fileService.findFileById(nonExistentId)).thenThrow(NotFoundException.class);

        MockHttpServletResponse response =  this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.FILES + ApiConstant.ID, nonExistentId)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser
    void givenCorrectFileRequestWhenCreateFileThenReturnFileResponseAnd201() throws Exception {
        given(this.mapper.toDomain(this.correctFileRequest)).willReturn(this.file);
        given(this.fileService.createFile(this.file)).willReturn(this.file);
        given(this.mapper.toResponse(this.file)).willReturn(this.fileResponse);

        MockHttpServletResponse response =  this.mockMvc.perform(
                multipart(ApiConstant.API_V1 + ApiConstant.FILES)
                        .file((MockMultipartFile) this.correctFileRequest.getFile())
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    @WithMockUser
    void givenIncorrectFileRequestWhenCreateFileThenReturn400() throws Exception {
        MockHttpServletResponse response =  this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.FILES)
                        .content(this.objectMapper.writeValueAsString(this.incorrectFileRequest))
                        .contentType("application/json")
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenCreateFileThenReturn403() throws Exception {
        MockHttpServletResponse response =  this.mockMvc.perform(
                multipart(ApiConstant.API_V1 + ApiConstant.FILES)
                        .file((MockMultipartFile) this.correctFileRequest.getFile())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenCorrectFileRequestAndExistentIdWhenUpdateFileThenReturnFileResponseAnd200() throws Exception {
        Long existentId = 1L;

        given(this.mapper.toDomain(this.correctFileRequest)).willReturn(this.file);
        given(this.fileService.updateFile(existentId, this.file)).willReturn(this.file);
        given(this.mapper.toResponse(this.file)).willReturn(this.fileResponse);

        MockHttpServletResponse response =  this.mockMvc.perform(
                multipart(HttpMethod.PUT, ApiConstant.API_V1 + ApiConstant.FILES + ApiConstant.ID, existentId)
                        .file((MockMultipartFile) this.correctFileRequest.getFile())
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    @WithMockUser
    void givenIncorrectFileRequestWhenUpdateFileThenReturn400() throws Exception {
        MockHttpServletResponse response =  this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.FILES + ApiConstant.ID, 1L)
                        .content(this.objectMapper.writeValueAsString(this.incorrectFileRequest))
                        .contentType("application/json")
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @WithMockUser
    void givenCorrectFileRequestAndNonExistentIdWhenUpdateFileThenReturn404() throws Exception {
        Long nonExistentId = 1L;

        given(this.mapper.toDomain(any())).willReturn(this.file);
        when(this.fileService.updateFile(anyLong(), any(File.class))).thenThrow(NotFoundException.class);

        MockHttpServletResponse response =  this.mockMvc.perform(
                multipart(HttpMethod.PUT, ApiConstant.API_V1 + ApiConstant.FILES + ApiConstant.ID, nonExistentId)
                        .file((MockMultipartFile) this.correctFileRequest.getFile())
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenUpdateFileThenReturn403() throws Exception {
        MockHttpServletResponse response =  this.mockMvc.perform(
                multipart(ApiConstant.API_V1 + ApiConstant.FILES + ApiConstant.ID, 1L)
                        .file((MockMultipartFile) this.correctFileRequest.getFile())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenExistentIdWhenDeleteFileThenReturn204() throws Exception {
        Long existentId = 1L;

        MockHttpServletResponse response =  this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.FILES + ApiConstant.ID, existentId)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    @WithMockUser
    void givenNonExistentIdWhenDeleteFileThenReturn404() throws Exception {
        Long nonExistentId = 1L;

        doThrow(NotFoundException.class).when(this.fileService).deleteFile(nonExistentId);

        MockHttpServletResponse response =  this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.FILES + ApiConstant.ID, nonExistentId)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenDeleteFileThenReturn403() throws Exception {
        MockHttpServletResponse response =  this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.FILES + ApiConstant.ID, 1L)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

}
