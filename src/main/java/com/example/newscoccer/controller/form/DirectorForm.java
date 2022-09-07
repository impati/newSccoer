package com.example.newscoccer.controller.form;

import com.example.newscoccer.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorForm extends DataTransferObject {
    private Long teamId;
    @NotBlank
    @Length(min = 1 , max = 20 ,message = "이름은 한글자 이상 20글자 이하여야합니다")
    private String directorName;
}
