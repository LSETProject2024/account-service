package uk.lset.controller;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.lset.model.BankAccount;
import uk.lset.model.BankAccountTransaction;
import uk.lset.model.User;
import uk.lset.request.BankAccountRequest;
import uk.lset.request.BankAccountTransactionRequest;
import uk.lset.request.MoneyTransferRequest;
import uk.lset.response.BankAccountResponse;
import uk.lset.response.MoneyTransferResponse;
import uk.lset.service.BankAccountService;
import uk.lset.service.BankAccountTransactionService;
import uk.lset.service.BankAccountTransactionTypeService;
import uk.lset.service.BankAccountTypeService;
import uk.lset.service.UserService;

@RestController
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private BankAccountTypeService bankAccountTypeService;

	@Autowired
	private BankAccountTransactionService bankAccountTransactionService;

	@Autowired
	private BankAccountTransactionTypeService bankAccountTransactionTypeService;

	@GetMapping("/myaccounts/{email}")
	public List<BankAccount> getMyAccounts(@PathVariable("email") String email) {
		logger.info("Get all accounts requested for email : " + email);
		User user = userService.findByEmail(email);
		logger.info(user.getEmail());
		return user.getAccounts();
	}

	@PostMapping(path = "/account/add", consumes = { "application/json" }, produces = "application/json")
	public BankAccountResponse addBankAccount(@RequestBody BankAccountRequest bankAccountRequest) {
		logger.debug("adding new BankAccount for bankaccounttype " + bankAccountRequest.getAccounttype());
		User user = userService.findByEmail(bankAccountRequest.getUser());

		BankAccountResponse bankAccountResponse = new BankAccountResponse();

		if (user != null) {
			BankAccount bankAccount = new BankAccount();

			bankAccount.setSortCode(ThreadLocalRandom.current().nextInt(100000, 1000000));
			bankAccount.setAccountNumber(ThreadLocalRandom.current().nextInt(10000000, 100000000));
			bankAccount.setBankAccountType(bankAccountTypeService.findById(bankAccountRequest.getAccounttype()));
			bankAccount.setBalance(0.00);
			BankAccount updatedBankAccount = bankAccountService.save(bankAccount);

			if (updatedBankAccount != null) {
				bankAccountResponse.setUser(user.getEmail());
				bankAccountResponse.setSortCode(updatedBankAccount.getSortCode());
				bankAccountResponse.setAccountNumber(updatedBankAccount.getAccountNumber());
				bankAccountResponse.setBankAccountType(updatedBankAccount.getBankAccountType());
			}
			List<BankAccount> userAccounts = user.getAccounts();
			userAccounts.add(updatedBankAccount);
			user.setAccounts(userAccounts);
			userService.save(user);
		}

		return bankAccountResponse;
	}

	@GetMapping("/byaccountnumber/{accountnumber}")
	public BankAccount findByAccountNumber(@PathVariable("accountnumber") Integer accountnumber) {
		logger.debug("BankAccount access requested for accountnumber : " + accountnumber);
		return bankAccountService.findByAccountNumber(accountnumber);
	}

	@PostMapping(path = "/addtransaction", consumes = { "application/json" }, produces = "application/json")
	public BankAccountResponse addBankAccountTransaction(
			@RequestBody BankAccountTransactionRequest bankAccountTransactionRequest) {

		return addTransaction(bankAccountTransactionRequest);
	}

	@PostMapping(path = "/transfer", consumes = { "application/json" }, produces = "application/json")
	public MoneyTransferResponse transferToAccount(@RequestBody MoneyTransferRequest moneyTransferRequest) {
		logger.info("transfer to " + moneyTransferRequest.getToAccount());

		BankAccount fromBankAccount = bankAccountService.findByAccountNumber(moneyTransferRequest.getFromAccount());

		MoneyTransferResponse moneyTransferResponse = new MoneyTransferResponse();

		if (fromBankAccount != null) {
			logger.info("found fromaccount");
			BankAccount toBankAccount = bankAccountService.findByAccountNumber(moneyTransferRequest.getToAccount());

			if (toBankAccount != null) {
				logger.info("found toaccount");
				BankAccountTransactionRequest bankAccountTransactionRequest = new BankAccountTransactionRequest();
				fromBankAccount.setBalance(fromBankAccount.getBalance() - moneyTransferRequest.getAmount());
				bankAccountTransactionRequest.setAccountNumber(fromBankAccount.getAccountNumber());
				bankAccountTransactionRequest.setAmount(moneyTransferRequest.getAmount());
				bankAccountTransactionRequest.setTransactionName("Transfering to " + toBankAccount.getAccountNumber());
				bankAccountTransactionRequest.setTransactionType("debit");
				this.addTransaction(bankAccountTransactionRequest);
				bankAccountTransactionRequest = null;

				bankAccountTransactionRequest = new BankAccountTransactionRequest();
				toBankAccount.setBalance(toBankAccount.getBalance() + moneyTransferRequest.getAmount());
				bankAccountTransactionRequest.setAccountNumber(toBankAccount.getAccountNumber());
				bankAccountTransactionRequest.setAmount(moneyTransferRequest.getAmount());
				bankAccountTransactionRequest.setTransactionName("Transfer from " + fromBankAccount.getAccountNumber());
				bankAccountTransactionRequest.setTransactionType("credit");
				this.addTransaction(bankAccountTransactionRequest);

			}
		}

		return moneyTransferResponse;
	}

	@PostMapping(path = "/cashdeposit", consumes = { "application/json" }, produces = "application/json")
	public MoneyTransferResponse cashDepositToAccount(@RequestBody MoneyTransferRequest moneyTransferRequest) {
		logger.debug("deposit to " + moneyTransferRequest.getToAccount());

		MoneyTransferResponse moneyTransferResponse = new MoneyTransferResponse();

		BankAccount toBankAccount = bankAccountService.findByAccountNumber(moneyTransferRequest.getToAccount());

		if (toBankAccount != null) {
			BankAccountTransactionRequest bankAccountTransactionRequest = new BankAccountTransactionRequest();
			toBankAccount.setBalance(toBankAccount.getBalance() + moneyTransferRequest.getAmount());
			bankAccountTransactionRequest.setAccountNumber(toBankAccount.getAccountNumber());
			bankAccountTransactionRequest.setAmount(moneyTransferRequest.getAmount());
			bankAccountTransactionRequest.setTransactionName("Cash Deposit");
			bankAccountTransactionRequest.setTransactionType("credit");
			this.addTransaction(bankAccountTransactionRequest);

		}

		return moneyTransferResponse;
	}

	@PostMapping(path = "/atmwithdraw", consumes = { "application/json" }, produces = "application/json")
	public MoneyTransferResponse atmWithdrawFromAccount(@RequestBody MoneyTransferRequest moneyTransferRequest) {
		logger.debug("ATM Withdraw from " + moneyTransferRequest.getToAccount());

		MoneyTransferResponse moneyTransferResponse = new MoneyTransferResponse();

		BankAccount toBankAccount = bankAccountService.findByAccountNumber(moneyTransferRequest.getToAccount());

		if (toBankAccount != null) {
			BankAccountTransactionRequest bankAccountTransactionRequest = new BankAccountTransactionRequest();
			toBankAccount.setBalance(toBankAccount.getBalance() - moneyTransferRequest.getAmount());
			bankAccountTransactionRequest.setAccountNumber(toBankAccount.getAccountNumber());
			bankAccountTransactionRequest.setAmount(moneyTransferRequest.getAmount());
			bankAccountTransactionRequest.setTransactionName("Cash Deposit");
			bankAccountTransactionRequest.setTransactionType("credit");
			this.addTransaction(bankAccountTransactionRequest);

		}

		return moneyTransferResponse;
	}

	private BankAccountResponse addTransaction(BankAccountTransactionRequest bankAccountTransactionRequest) {
		logger.info("transaction adding");
		BankAccount bankAccount = bankAccountService
				.findByAccountNumber(bankAccountTransactionRequest.getAccountNumber());
		BankAccountResponse bankAccountResponse = new BankAccountResponse();

		BankAccount updatedBankAccount = null;
		if (bankAccount != null) {
			BankAccountTransaction transaction = new BankAccountTransaction();
			transaction.setAmount(bankAccountTransactionRequest.getAmount());
			transaction.setTransactionName(bankAccountTransactionRequest.getTransactionName());
			transaction.setBankAccountTransactionType(
					bankAccountTransactionTypeService.findByType(bankAccountTransactionRequest.getTransactionType()));
			BankAccountTransaction updatedTransaction = bankAccountTransactionService.save(transaction);
			logger.info("saving transaction");
			List<BankAccountTransaction> existingTransactions = bankAccount.getTransactions();
			existingTransactions.add(updatedTransaction);
			bankAccount.setTransactions(existingTransactions);
			updatedBankAccount = bankAccountService.save(bankAccount);

			if (updatedBankAccount != null) {
				bankAccountResponse.setSortCode(updatedBankAccount.getSortCode());
				bankAccountResponse.setAccountNumber(updatedBankAccount.getAccountNumber());
			}
		}

		return bankAccountResponse;
	}

}
