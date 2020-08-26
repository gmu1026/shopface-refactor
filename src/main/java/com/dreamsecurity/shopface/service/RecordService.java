package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.record.RecordEditRequestDto;
import com.dreamsecurity.shopface.dto.record.RecordListResponse;

import java.util.List;

public interface RecordService {
    List<RecordListResponse> getRecordList(String memberId);
    List<RecordListResponse> getBranchRecordList(Long no);
    Long editRecord(long no, RecordEditRequestDto requestDto);
    void removeRecord(long no);
}
