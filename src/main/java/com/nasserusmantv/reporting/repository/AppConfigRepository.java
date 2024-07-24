package com.nasserusmantv.reporting.repository;

import com.nasserusmantv.reporting.entity.AppConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigRepository extends JpaRepository<AppConfigEntity, Long> {

	@Query("SELECT configValue FROM AppConfigEntity WHERE configName = ?1")
	String findConfigValueByConfigName(String configName);
}
