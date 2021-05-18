package com.depromeet.muyaho.domain.domain.member;

import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "uni_member_1", columnNames = {"uid", "provider"}),
        @UniqueConstraint(name = "uni_member_2", columnNames = "name")
    }
)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uid;

    private Email email;

    @Column(nullable = false)
    private String name;

    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberProvider provider;

    private Member(String uid, String email, String name, String profileUrl, MemberProvider provider) {
        this.uid = uid;
        this.email = email == null ? null : Email.of(email);
        this.name = name;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

    public static Member newInstance(String uid, String email, String name, String profileUrl, MemberProvider provider) {
        return new Member(uid, email, name, profileUrl, provider);
    }

    public void updateMemberInfo(String name, String profileUrl) {
        this.name = name;
        this.profileUrl = profileUrl;
    }

    public DeleteMember delete() {
        return DeleteMember.builder()
            .previousId(id)
            .name(name)
            .email(getEmail())
            .uid(uid)
            .profileUrl(profileUrl)
            .provider(provider)
            .build();
    }

    public String getEmail() {
        if (this.email == null) {
            return null;
        }
        return this.email.getEmail();
    }

}
