package com.tplate.layers.a.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String message;
    private String details;
    private Object data;

    // Custom Setter for Builder
    public static class ResponseDtoBuilder {

        private static final ModelMapper modelMapper = new ModelMapper();

        public ResponseDtoBuilder data(Object data, Class dto){
            this.data = modelMapper.map(data, dto);
            return this;
        }

    }
}
