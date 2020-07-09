package com.luntan.dto;

import lombok.Data;


@Data
public class ResultDTO<T>{
    private T data;
    public static<T> ResultDTO ok(T t){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setData(t);
        return resultDTO;
    }
}
