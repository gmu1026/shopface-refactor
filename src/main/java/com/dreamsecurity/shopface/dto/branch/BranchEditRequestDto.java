package com.dreamsecurity.shopface.dto.branch;

import com.dreamsecurity.shopface.domain.Branch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class BranchEditRequestDto {
  private String name;
  private String address;
  private String detailAddress;
  private String zipCode;
  private MultipartFile businessLicenseImage;

  public BranchEditRequestDto(Branch entity) {
    this.name = entity.getName();
    this.address = entity.getAddress();
    this.detailAddress = entity.getDetailAddress();
    this.zipCode = entity.getZipCode();
  }
}
