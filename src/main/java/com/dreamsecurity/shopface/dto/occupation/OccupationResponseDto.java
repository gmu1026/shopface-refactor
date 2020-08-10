package com.dreamsecurity.shopface.dto.occupation;

import com.dreamsecurity.shopface.domain.Occupation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OccupationResponseDto {
    private long no;
    private long branchNo;
    private String name;
}
