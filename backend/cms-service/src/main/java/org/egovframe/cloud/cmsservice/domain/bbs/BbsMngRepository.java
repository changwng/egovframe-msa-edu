package org.egovframe.cloud.cmsservice.domain.bbs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BbsMngRepository extends JpaRepository<BbsMng, Integer> , BbsMngRepositoryCustom {
}
