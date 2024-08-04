package com.example.recruitmenttaskv01.mapper;




import com.example.recruitmenttaskv01.model.dto.BranchDTO;
import com.example.recruitmenttaskv01.model.dto.RepositoryDTO;
import com.example.recruitmenttaskv01.model.githubRepositoryModel.Repository;
import com.example.recruitmenttaskv01.model.githubRepositoryModel.branch.Branch;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryMapper {

    public static RepositoryDTO mapToRepositoryDTO(Repository repository) {
        return new RepositoryDTO(repository.getName(), repository.getOwner().login(), mapToBranchDTOList(repository.getBranchList()));
    }

    public static List<BranchDTO> mapToBranchDTOList(List<Branch> branches) {
        return branches.stream().map(RepositoryMapper::mapToBranchDTO).collect(Collectors.toList());
    }

    public static BranchDTO mapToBranchDTO(Branch branch) {
        return new BranchDTO(branch.name(), branch.commit().sha());
    }
}