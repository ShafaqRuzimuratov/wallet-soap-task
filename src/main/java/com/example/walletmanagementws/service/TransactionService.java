package com.example.walletmanagementws.service;

import com.example.walletmanagementws.entity.Transaction;
import com.example.walletmanagementws.entity.User;
import com.example.walletmanagementws.entity.Wallet;
import com.example.walletmanagementws.enums.TrnStatus;
import com.example.walletmanagementws.enums.TrnType;
import com.example.walletmanagementws.models.*;
import com.example.walletmanagementws.repository.TransactionsRepository;
import com.example.walletmanagementws.repository.UserRepository;
import com.example.walletmanagementws.repository.WalletRepository;
import com.example.walletmanagementws.utils.CurrentTimestampUtil;
import com.example.walletmanagementws.utils.GenericParamKeys;
import com.example.walletmanagementws.utils.StatusConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionsRepository transactionsRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public PerformTransactionResult performTransaction(PerformTransactionArguments performTransactionArguments) {
        if (performTransactionArguments.getAmount() > 124500000) {
            PerformTransactionResult performTransactionResult = new PerformTransactionResult();
            performTransactionResult.setStatus(StatusConstants.ERROR_MAX_VALUE);
            performTransactionResult.setErrorMsg("max_value_over");
            return performTransactionResult;
        }

        String walletIdStr = getValue(performTransactionArguments.getParameters(), GenericParamKeys.wallet_id);
        if (Objects.isNull(walletIdStr)) {
            throw new UnsupportedOperationException("please_sent_wallet_id");
        }
        long walletId = Long.parseLong(walletIdStr);
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isEmpty()) {
            PerformTransactionResult performTransactionResult = new PerformTransactionResult();
            performTransactionResult.setStatus(StatusConstants.WALLET_NOT_FOUND);
            performTransactionResult.setErrorMsg("wallet_not_found");
            return performTransactionResult;
        }

        Optional<User> userByUserName = userRepository.findByUsername(performTransactionArguments.getUsername());
        if (userByUserName.isEmpty()) {
            PerformTransactionResult performTransactionResult = new PerformTransactionResult();
            performTransactionResult.setStatus(StatusConstants.USER_NOT_FOUND);
            performTransactionResult.setErrorMsg("unexpected_error");
            return performTransactionResult;
        }
        User user = userByUserName.get();
        Wallet wallet = optionalWallet.get();

        if (user.getBalance() < performTransactionArguments.getAmount()) {
            PerformTransactionResult performTransactionResult = new PerformTransactionResult();
            performTransactionResult.setStatus(StatusConstants.INSUFFICIENT_BALANCE);
            performTransactionResult.setErrorMsg("insufficient_balance");
            return performTransactionResult;
        }

        user.setBalance(user.getBalance() - performTransactionArguments.getAmount());
        wallet.setWalletBalance(wallet.getWalletBalance() + performTransactionArguments.getAmount());

        userRepository.save(user);
        walletRepository.save(wallet);

        Transaction transaction = new Transaction(
                wallet,
                performTransactionArguments.getAmount(),
                TrnType.CREDIT,
                TrnStatus.SUCCESS,
                performTransactionArguments.getTransactionId()
        );

        Transaction trn = transactionsRepository.save(transaction);

        PerformTransactionResult performTransactionResult = new PerformTransactionResult();
        performTransactionResult.setStatus(StatusConstants.OK);
        performTransactionResult.setErrorMsg("Ok");
        performTransactionResult.setProviderTrnId(trn.getId());
        performTransactionResult.setTimeStamp(CurrentTimestampUtil.getCurrentTimestamp());
        GenericParam genericParam = new GenericParam();
        genericParam.setParamKey(GenericParamKeys.balance);
        genericParam.setParamValue(String.valueOf(wallet.getWalletBalance()));
        performTransactionResult.getParameters().add(genericParam);
        return performTransactionResult;
    }

    @Transactional
    public GetInformationResult getInformation(GetInformationArguments getInformationArguments) {
        GetInformationResult getInformationResult = new GetInformationResult();

        Optional<User> userOptional = userRepository.findByUsername(getInformationArguments.getUsername());
        if (userOptional.isEmpty()) {
            getInformationResult.setStatus(StatusConstants.USER_NOT_FOUND);
            getInformationResult.setErrorMsg("user_not_found");
            getInformationResult.setTimeStamp(CurrentTimestampUtil.getCurrentTimestamp());
            return getInformationResult;
        }

        User user = userOptional.get();
        Optional<Wallet> walletOptional = walletRepository.findByUserId(user.getId());
        if (walletOptional.isEmpty()) {
            getInformationResult.setStatus(StatusConstants.WALLET_NOT_FOUND);
            getInformationResult.setErrorMsg("wallet_not_found");
            getInformationResult.setTimeStamp(CurrentTimestampUtil.getCurrentTimestamp());
            return getInformationResult;
        }

        Wallet wallet = walletOptional.get();

        GenericParam balanceParam = new GenericParam();
        balanceParam.setParamKey(GenericParamKeys.balance);
        balanceParam.setParamValue(String.valueOf(wallet.getWalletBalance()));

        GenericParam nameParam = new GenericParam();
        nameParam.setParamKey(GenericParamKeys.name);
        nameParam.setParamValue(user.getUsername());

        getInformationResult.getParameters().add(balanceParam);
        getInformationResult.getParameters().add(nameParam);

        getInformationResult.setStatus(StatusConstants.OK);
        getInformationResult.setErrorMsg("OK");
        getInformationResult.setTimeStamp(CurrentTimestampUtil.getCurrentTimestamp());

        return getInformationResult;
    }



    private String getValue(List<GenericParam> genericParams, String key) {
        if (genericParams == null)
            return null;
        else {
            Optional<GenericParam> optionalGenericParam = genericParams.stream().takeWhile(genericParam -> genericParam.getParamKey().equals(key))
                    .findFirst();
            return optionalGenericParam.map(GenericParam::getParamValue).orElse(null);
        }
    }
}
