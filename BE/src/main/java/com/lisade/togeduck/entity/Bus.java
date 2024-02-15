package com.lisade.togeduck.entity;

import com.lisade.togeduck.entity.enums.BusType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BusType type;

    @Column(name = "number_of_seats", nullable = false)
    private Integer numberOfSeats;

    @Column(name = "row", nullable = false)
    private Integer row;

    @Column(name = "column", nullable = false)
    private Integer column;

    @Column(name = "back_seat", nullable = false)
    private Integer backSeat;

    @OneToMany(mappedBy = "bus", fetch = FetchType.LAZY)
    private List<Route> route;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<PriceTable> priceTables;
}
