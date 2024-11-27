package com.company.JobPortal.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomResponse <T>{
    String message;
    T obj;
    boolean success;
}
