package com.example.walletmanagementws.entity;

import com.example.walletmanagementws.enums.TrnStatus;
import com.example.walletmanagementws.enums.TrnType;
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
@Table(name = "transactions")
@Entity(name = "Transaction")
public class Transaction extends BaseEntityWithSequence implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column(name = "trn_amount")
    private Long trnAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "trn_type")
    private TrnType trnType;

    @Enumerated(EnumType.STRING)
    @Column(name = "trn_status")
    private TrnStatus trnStatus;

    @Column(nullable = false, name = "agent_transaction_id")
    private Long agentTransactionId;
}
