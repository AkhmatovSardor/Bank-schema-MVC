package com.example.Bank.Schema.service;

import com.example.Bank.Schema.dto.BranchDto;
import com.example.Bank.Schema.dto.ErrorDto;
import com.example.Bank.Schema.dto.ResponseDto;
import com.example.Bank.Schema.entity.Branch;
import com.example.Bank.Schema.repository.BranchRepository;
import com.example.Bank.Schema.service.mapper.BranchMapper;
import com.example.Bank.Schema.service.validate.BranchValidate;
import com.example.Bank.Schema.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService implements SimpleCrud<Integer, BranchDto> {
    private final BranchMapper branchMapper;
    private final BranchRepository branchRepository;
    private final BranchValidate branchValidation;

    @Override
    public ResponseDto<BranchDto> create(BranchDto dto) {
        List<ErrorDto> errors = this.branchValidation.valid(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<BranchDto>builder()
                    .message("Validate error!")
                    .code(-2)
                    .errors(errors)
                    .build();
        }
        try {
            Branch branch=this.branchMapper.toEntity(dto);
            this.branchRepository.save(branch);
        return ResponseDto.<BranchDto>builder()
                 //   .data(this.branchMapper.toDto(this.branchRepository.save(this.branchMapper.toEntity(dto))))
                    .data(this.branchMapper.toDto(branch))
                    .message("Branch successful created!")
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<BranchDto>builder()
                    .message("While saving error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<BranchDto> get(Integer id) {
        return this.branchRepository.findByBranchIdAndDeletedAtIsNull(id)
                .map(branch ->
                        ResponseDto.<BranchDto>builder()
                                .message("OK")
                                .success(true)
                                .data(this.branchMapper.toDtoWithAll(branch))
                                .build()
                ).orElse(ResponseDto.<BranchDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<BranchDto> update(Integer id, BranchDto dto) {
        try {
            return this.branchRepository.findByBranchIdAndDeletedAtIsNull(id)
                    .map(branch -> {
                        this.branchMapper.update(branch, dto);
                        this.branchRepository.save(branch);
                        return ResponseDto.<BranchDto>builder()
                                .message("Brunch successful updated!")
                                .success(true)
                                .data(this.branchMapper.toDto(branch))
                                .build();
                    })
                    .orElse(ResponseDto.<BranchDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<BranchDto>builder()
                    .message("While updating error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<BranchDto> delete(Integer id) {
        try {
            return this.branchRepository.findByBranchIdAndDeletedAtIsNull(id)
                    .map(branch -> {
                        branch.setDeletedAt(LocalDateTime.now());
                        this.branchRepository.save(branch);
                        return ResponseDto.<BranchDto>builder()
                                .message("Brunch successful deleted!")
                                .success(true)
                                .data(this.branchMapper.toDto(branch))
                                .build();
                    })
                    .orElse(ResponseDto.<BranchDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<BranchDto>builder()
                    .message("While deleting error!")
                    .code(-3)
                    .build();
        }
    }
}
