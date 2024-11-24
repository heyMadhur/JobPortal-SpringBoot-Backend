package com.company.JobPortal.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomResponse <T>{
    String message;
    T obj;
    boolean success;
}
