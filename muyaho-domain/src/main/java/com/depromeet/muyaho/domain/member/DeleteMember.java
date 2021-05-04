package com.depromeet.muyaho.domain.member;

import com.depromeet.muyaho.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeleteMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long previousId;

    @Column(nullable = false)
    private String uid;

    private Email email;

    @Column(nullable = false)
    private String name;

    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberProvider provider;

    @Builder
    public DeleteMember(Long previousId, String uid, String email, String name, String profileUrl, MemberProvider provider) {
        this.previousId = previousId;
        this.uid = uid;
        this.email = email == null ? null : Email.of(email);
        this.name = name;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

}
