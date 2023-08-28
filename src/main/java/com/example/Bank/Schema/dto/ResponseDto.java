package com.example.Bank.Schema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private int code;
//    0 -> Ok
//   -1 -> not found
//   -2 -> validation error
//   -3 -> database error
    private String message;
    private boolean success;
    private T data;
    private List<ErrorDto> errors;
}
