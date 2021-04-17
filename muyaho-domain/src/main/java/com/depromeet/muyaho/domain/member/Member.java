package com.depromeet.muyaho.domain.member;

import com.depromeet.muyaho.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Column(nullable = false)
    private String name;

    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberProvider provider;

    private Member(String email, String name, String profileUrl, MemberProvider provider) {
        this.email = Email.of(email);
        this.name = name;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

    public static Member newInstance(String email, String name, String profileUrl, MemberProvider provider) {
        return new Member(email, name, profileUrl, provider);
    }

    public String getEmail() {
        return this.email.getEmail();
    }

}
