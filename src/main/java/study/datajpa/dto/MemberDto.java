package study.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

}
