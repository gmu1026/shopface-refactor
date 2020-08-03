package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.record.RecordAddRequestDto;
import com.dreamsecurity.shopface.dto.record.RecordEditRequestDto;
import com.dreamsecurity.shopface.dto.record.RecordListResponse;

import java.util.List;

public interface RecordService {
    Long addRecord(RecordAddRequestDto requestDto);
    List<RecordListResponse> getRecordList(String memberId);
    Long editRecord(long no, RecordEditRequestDto requestDto);
    void removeRecord(long no);
}
