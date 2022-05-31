package com.springDataRest.SpringDataRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.springDataRest.SpringDataRest.entity.GradleUser;

@RepositoryRestResource(path = "members")
public interface GradleUserRepository  extends JpaRepository<GradleUser , Long>{

}
