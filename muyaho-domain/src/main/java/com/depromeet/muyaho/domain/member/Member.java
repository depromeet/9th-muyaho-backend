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

    private String name;

    private String profileUrl;

    private Member(String email, String name, String profileUrl) {
        this.email = Email.of(email);
        this.name = name;
        this.profileUrl = profileUrl;
    }

    public static Member newInstance(String email, String name, String profileUrl) {
        return new Member(email, name, profileUrl);
    }

    public String getEmail() {
        return this.email.getEmail();
    }

}
