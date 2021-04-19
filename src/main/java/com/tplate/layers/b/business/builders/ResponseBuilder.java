package com.tplate.layers.b.business.builders;

import com.tplate.layers.a.rest.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    private String mesage = "";
    private Object dto;
    private HttpStatus statusCode = HttpStatus.OK;



    private ResponseBuilder(){}

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public ResponseBuilder message(String message) {
        this.mesage = message;
        return this;
    }

    public ResponseBuilder dto(Object object) {
        this.dto = object;
        return this;
    }

    public ResponseBuilder statusCode(HttpStatus httpStatus) {
        this.statusCode = httpStatus;
        return this;
    }

    public ResponseEntity build() {
        ResponseDto responseDto = ResponseDto.builder()
                .message(this.mesage)
//                .data(this.dto)
                .build();
        return new ResponseEntity(responseDto, this.statusCode);
    }

//    **************************************************************************
//    * Shortcuts Responses: Only message and status code.
//    **************************************************************************

    public static ResponseEntity buildConflict(String message) {
        return ResponseBuilder
                .builder()
                .conflict()
                .message(message)
                .build();
    }

    public static ResponseEntity buildBadRequest(String message) {
        return ResponseBuilder
                .builder()
                .badRequest()
                .message(message)
                .build();
    }

    public static ResponseEntity buildOk(String message) {
        return ResponseBuilder
                .builder()
                .ok()
                .message(message)
                .build();
    }

    public static ResponseEntity buildNotFound(String message) {
        return ResponseBuilder
                .builder()
                .notFound()
                .message(message)
                .build();
    }

    public static ResponseEntity buildInternalServerError(String message) {
        return ResponseBuilder
                .builder()
                .internalServerError()
                .message(message)
                .build();
    }

    public static ResponseEntity buildForbidden(String message) {
        return ResponseBuilder
                .builder()
                .forbidden()
                .message(message)
                .build();
    }

    public static ResponseEntity buildSomethingWrong() {
        return ResponseBuilder
                .builder()
                .internalServerError()
                .message("Something went wrong.")
                .build();
    }

//    **************************************************************************
//    * Shortcuts Status Code
//    **************************************************************************

    public ResponseBuilder badRequest() {
        this.statusCode = HttpStatus.BAD_REQUEST;
        return this;
    }

    public ResponseBuilder ok() {
        this.statusCode = HttpStatus.OK;
        return this;
    }

    public ResponseBuilder conflict() {
        this.statusCode = HttpStatus.CONFLICT;
        return this;
    }

    public ResponseBuilder notFound() {
        this.statusCode = HttpStatus.NOT_FOUND;
        return this;
    }

    public ResponseBuilder internalServerError() {
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public ResponseBuilder forbidden() {
        this.statusCode = HttpStatus.FORBIDDEN;
        return this;
    }


}
