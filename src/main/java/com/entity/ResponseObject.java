package com.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 长不大的韭菜
 * @date 2020/5/30 11:19 下午
 */
@AllArgsConstructor
@NotEmpty
@Builder
@Data
public class ResponseObject implements Serializable {
    private static final long serialVersionUID = 6902986126328263093L;

    private String errorMessage;
}
