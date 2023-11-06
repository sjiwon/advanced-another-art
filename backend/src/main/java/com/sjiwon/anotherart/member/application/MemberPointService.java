package com.sjiwon.anotherart.member.application;

import com.sjiwon.anotherart.member.domain.model.Member;
import com.sjiwon.anotherart.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberPointService {
    private final MemberRepository memberRepository;

    // TODO Point 도메인 분리 후 리팩토링

    public void chargePoint(final Long memberId, final Integer amount) {
        final Member member = memberRepository.getById(memberId);
//        member.addPointRecords(CHARGE, amount);
    }

    public void refundPoint(final Long memberId, final Integer amount) {
        final Member member = memberRepository.getById(memberId);
//        member.addPointRecords(REFUND, amount);
    }
}
