package com.yujapay.membership.adapter.out.persistence;

import com.yujapay.membership.application.port.out.FindMembershipPort;
import com.yujapay.membership.application.port.out.RegisterMembershipPort;
import com.yujapay.membership.domain.Membership;
import common.PersistenceAdaptor;
import lombok.RequiredArgsConstructor;

@PersistenceAdaptor
@RequiredArgsConstructor
public class MembershipPersistenceAdaptor implements RegisterMembershipPort, FindMembershipPort {
    // adaptor 가 실제 어떻게 연동될지 여기서 정의됨

    private final SpringDataMembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        return membershipRepository.save(
            new MembershipJpaEntity(
                  membershipName.getNameValue(),
                  membershipEmail.getEmailValue(),
                  membershipAddress.getAddressValue(),
                  membershipIsValid.isValidValue(),
                  membershipIsCorp.isCorpValue()
            )
        );
    }

    @Override
    public Membership findMembership(Membership.MembershipId membershipId) {
        return membershipMapper.mapToDomainEntity(membershipRepository.getReferenceById(Long.parseLong(membershipId.getMembershipId())));
    }
}