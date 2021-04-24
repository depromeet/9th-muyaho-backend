package com.depromeet.muyaho.domain.stock;

import com.depromeet.muyaho.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private StockType type;

    @Column(nullable = false)
    private String stockCode;

    @Column(nullable = false)
    private int purchasePrice;

    @Column(nullable = false)
    private int quantity;

}
