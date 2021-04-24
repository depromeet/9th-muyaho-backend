package com.depromeet.muyaho.domain.stock;

import com.depromeet.muyaho.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uni_stock_1", columnNames = {"type", "code"}))
public class Stock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StockMarketType type;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private StockStatus status;

    Stock(StockMarketType type, String code, String name, StockStatus status) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public static Stock newInstance(StockMarketType type, String code, String name) {
        return new Stock(type, code, name, StockStatus.ACTIVE);
    }

    public Stock disable() {
        this.status = StockStatus.DISABLED;
        return this;
    }

    public Stock active() {
        this.status = StockStatus.ACTIVE;
        return this;
    }

}
