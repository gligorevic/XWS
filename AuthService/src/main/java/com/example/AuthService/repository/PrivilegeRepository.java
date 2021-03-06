package com.example.AuthService.repository;

import com.example.AuthService.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Query(value = "SELECT * FROM PRIVILEGE AS pr INNER JOIN (SELECT DISTINCT privilege_id FROM ROLES_PRIVILEGES AS rp WHERE rp.role_id in ?1) AS rp1 ON  pr.id = rp1.privilege_id" , nativeQuery=true)
    List<Privilege> findByRolesIn(@Param("roleIds") Collection<Long> roleIds);

    @Query(value = "SELECT * FROM PRIVILEGE AS pr INNER JOIN (SELECT * FROM USER_BLOCKED_PRIVILEGES AS ubp WHERE ubp.user_id = ?1) AS ubp1 ON  pr.id = ubp1.privilege_id" , nativeQuery=true)
    List<Privilege> findBlockedPrivilegesByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM PRIVILEGE AS pr WHERE pr.ID NOT IN  (SELECT PRIVILEGE_ID FROM ROLES_PRIVILEGES AS RP WHERE  RP.ROLE_ID IN (SELECT ID FROM ROLE AS r WHERE r.NAME = 'ROLE_ENDUSER' OR r.NAME = 'ROLE_ADMIN'))", nativeQuery = true)
    List<Privilege> findAgentSpecificPrivileges();
}
