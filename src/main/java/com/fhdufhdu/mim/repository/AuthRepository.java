package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    @Query(value = "select a from Auth a where a.member.id = :memberId and a.token = :token")
    Optional<Auth> findByMemberIdAndToken(@Param("memberId") String memberId, @Param("token") String token);
}
