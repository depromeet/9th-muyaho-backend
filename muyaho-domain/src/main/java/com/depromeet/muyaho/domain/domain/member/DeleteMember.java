package com.depromeet.muyaho.domain.domain.member;

import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
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

    @Column(nullable = false)
    private Long previousId;

    @Column(nullable = false, length = 50)
    private String uid;

    private Email email;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 2048)
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
