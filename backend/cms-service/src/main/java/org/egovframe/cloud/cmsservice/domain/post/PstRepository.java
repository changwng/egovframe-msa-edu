package org.egovframe.cloud.cmsservice.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PstRepository extends JpaRepository<Pst, PstId>, PstRepositoryCustom {
}
