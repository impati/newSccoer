package com.example.newscoccer.controller.form;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorForm extends DataTransferObject {
    private Long teamId;
    private String directorName;
}
