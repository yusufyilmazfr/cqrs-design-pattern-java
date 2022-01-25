package com.example.cqrsdesignpatternjava.core.cq.command.impl.classified;

import com.example.cqrsdesignpatternjava.core.cq.command.Command;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateClassifiedCommand implements Command, Serializable {
    private String title;
    private String detail;
    private Double price;
    private Long categoryId;
}