package com.hermanovich.accountingsystem.model.request;

import com.hermanovich.accountingsystem.model.AEntity;
import com.hermanovich.accountingsystem.model.car.Car;
import com.hermanovich.accountingsystem.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "requests")
public class Request extends AEntity {

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id", updatable = false)
    private Car car;
}
