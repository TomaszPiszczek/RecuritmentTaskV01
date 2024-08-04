package com.example.recruitmenttaskv01.model.githubRepositoryModel;

import com.example.recruitmenttaskv01.model.githubRepositoryModel.branch.Branch;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Repository {
    @SerializedName("name")
    private String name;
    private boolean fork;
    private Owner owner;
    private List<Branch> branchList;
}