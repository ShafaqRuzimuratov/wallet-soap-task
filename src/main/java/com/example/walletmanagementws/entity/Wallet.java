package com.example.walletmanagementws.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "wallets")
@Entity(name = "Wallet")
public class Wallet extends BaseEntityWithSequence implements Serializable {
    private static final long serialVersionUID = 1L;

    @JoinColumn(insertable = false, updatable = false, name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "wallet_number", unique = true)
    private String walletNumber;

    @Column(name = "wallet_balance", nullable = false)
    private Long walletBalance = 0L;

    @Column(name = "wallet_name")
    private String walletName;
}
