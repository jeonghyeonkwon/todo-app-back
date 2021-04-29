package com.givejeong.todo.repository;

import com.givejeong.todo.domain.ProgrammingRole;
import com.givejeong.todo.repository.querydsl.ProgrammingRoleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammingRoleRepository extends JpaRepository<ProgrammingRole,Long>, ProgrammingRoleRepositoryCustom {
}
