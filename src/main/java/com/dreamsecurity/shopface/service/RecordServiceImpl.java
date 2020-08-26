package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Record;
import com.dreamsecurity.shopface.dto.record.RecordEditRequestDto;
import com.dreamsecurity.shopface.dto.record.RecordListResponse;
import com.dreamsecurity.shopface.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RecordService {
  private final RecordRepository recordRepository;

  @Transactional(readOnly = true)
  @Override
  public List<RecordListResponse> getRecordList(String memberId) {
    return recordRepository.findAllByMemberId(memberId).stream()
        .map(RecordListResponse::new)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  @Override
  public List<RecordListResponse> getBranchRecordList(Long no) {
    return recordRepository.findAllByBranchNo(no).stream()
            .map(RecordListResponse::new)
            .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public Long editRecord(long no, RecordEditRequestDto requestDto) {
    Record entity =
        recordRepository
            .findById(no)
            .orElseThrow(() -> new IllegalArgumentException("해당 기록이 존재하지 않습니다."));

    entity.update(
        requestDto.getNote(),
        requestDto.getWorkingTime(),
        requestDto.getQuittingTime(),
        requestDto.getSalaryPay());

    return no;
  }

  @Transactional
  @Override
  public void removeRecord(long no) {
    Record entity =
        recordRepository
            .findById(no)
            .orElseThrow(() -> new IllegalArgumentException("해당 기록이 존재하지 않습니다."));

    recordRepository.delete(entity);
  }
}
