package com.starlight.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * doc
 *
 * @author shilian
 * @since 2023-08-14  18:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoVo {
    private int num1;

    private int num2;
}
