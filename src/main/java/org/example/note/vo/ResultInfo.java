package org.example.note.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * encapsulate info returned
 * succeed = 1, fail = 0
 *
 */
@Getter
@Setter
public class ResultInfo<T> {

    private Integer code;
    private String msg;
    private T result;


}
