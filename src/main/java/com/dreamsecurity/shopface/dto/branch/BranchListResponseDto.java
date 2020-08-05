package com.dreamsecurity.shopface.dto.branch;

import com.dreamsecurity.shopface.domain.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;

@ToString
@Getter
@AllArgsConstructor
public class BranchListResponseDto {
  private long no;
  private String name;
  private String businessLicensePath;
  private String state;
  private LocalDateTime registerDate;

  public BranchListResponseDto(Branch entity) {
    this.no = entity.getNo();
    this.name = entity.getName();
    this.businessLicensePath = entity.getBusinessLicensePath();
    this.state = entity.getState();
    this.registerDate = entity.getRegisterDate();
  }
}
