package com.sjiwon.anotherart.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByNameAndEmail(String name, Email email);
    boolean existsByNickname(String nickname);
    boolean existsByLoginId(String loginId);
    boolean existsByPhone(String phone);
    boolean existsByEmail(Email email);
    boolean existsByLoginIdAndNameAndEmail(String loginId, String name, Email email);
}
