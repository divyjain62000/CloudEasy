package com.cloudeasy.repository.os;

import com.cloudeasy.domain.os.OperatingSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage operating system related things
 */
@Repository
public interface OperatingSystemRepository extends JpaRepository<OperatingSystem,Integer> {
}
