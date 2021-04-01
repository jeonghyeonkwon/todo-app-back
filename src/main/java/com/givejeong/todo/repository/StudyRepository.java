package com.givejeong.todo.repository;

import com.givejeong.todo.domain.FieldEnum;
import com.givejeong.todo.domain.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyRepository extends JpaRepository<Study,Long> {
    @Query("SELECT std FROM Study std WHERE std.fieldEnum =:field")
    Page<Study> findSectionStudy(Pageable pageable, @Param("field")FieldEnum fieldEnum);

    //Page<Study> findStudy(Pageable pageable);
}
