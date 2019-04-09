package com.example.accountbanking.controler;

import com.example.accountbanking.dao.AccountDao;
import com.example.accountbanking.dao.LegalCostumerDao;
import com.example.accountbanking.dao.RealCostumerDao;
import com.example.accountbanking.dao.TransactionDao;
import com.example.accountbanking.dto.*;
import com.example.accountbanking.dto.ResponseStatus;
import com.example.accountbanking.entity.Account;
import com.example.accountbanking.entity.AccountTransaction;
import com.example.accountbanking.entity.LegalCostumer;
import com.example.accountbanking.entity.RealCostumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@RestController
public class ServiceController {

    //  @Autowired
    private LegalCostumerDao legalCostumerDao;
    private RealCostumerDao realCostumerDao;
    private AccountDao accountDao;
    private TransactionDao transactionDao;
    private  static final Logger log= LogManager.getLogger();

    public ServiceController(LegalCostumerDao legalCostumerDao, RealCostumerDao realCostumerDao, AccountDao accountDao, TransactionDao transactionDao) {
        this.legalCostumerDao = legalCostumerDao;
        this.realCostumerDao = realCostumerDao;
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    @RequestMapping(value = "/ws/menu/getUserMenu", method = RequestMethod.POST)
    public ResponseDto<MenuItmDto> getUserMenu() {
        MenuItmDto menuItmDto = new MenuItmDto(null, null, null, new ArrayList<>(Arrays.asList(
                new MenuItmDto(MenuItemType.MENU, "امور مشتریان", null, new ArrayList<MenuItmDto>(Arrays.asList(
                        new MenuItmDto(MenuItemType.MENU, "مشتریان حقوقی", null, new ArrayList<MenuItmDto>(Arrays.asList(
                                new MenuItmDto(MenuItemType.PAGE, "ثبت حساب مشتری حقوقی", new UIPageDto(null, "addLegal.xml"), new ArrayList<MenuItmDto>()),
                                new MenuItmDto(MenuItemType.PAGE, "ویرایش حساب مشتری حقوقی", new UIPageDto(null, "editLegal.xml"), new ArrayList<MenuItmDto>()),
                                new MenuItmDto(MenuItemType.PAGE, "نمایش حساب مشتری حقوقی", new UIPageDto(null, "showLegal.xml"), new ArrayList<MenuItmDto>())))),
                        new MenuItmDto(MenuItemType.MENU, "مشتریان حقیقی", null, new ArrayList<MenuItmDto>(Arrays.asList(
                                new MenuItmDto(MenuItemType.PAGE, "ثبت حساب مشتری حقیقی", new UIPageDto(null, "addReal.xml"), new ArrayList<MenuItmDto>()),
                                new MenuItmDto(MenuItemType.PAGE, "ویرایش حساب مشتری حقیقی", new UIPageDto(null, "editReal.xml"), new ArrayList<MenuItmDto>()),
                                new MenuItmDto(MenuItemType.PAGE, "نمایش حساب مشتری حقیقی", new UIPageDto(null, "showReal.xml"), new ArrayList<MenuItmDto>())))),
                        new MenuItmDto(MenuItemType.MENU, "عملیات بانکی", null, new ArrayList<MenuItmDto>(Arrays.asList(
                                new MenuItmDto(MenuItemType.PAGE, "برداشت وجه", new UIPageDto(null, "take.xml"), new ArrayList<MenuItmDto>()),
                                new MenuItmDto(MenuItemType.PAGE, "واریز وجه", new UIPageDto(null, "deposit.xml"), new ArrayList<MenuItmDto>()),
                                new MenuItmDto(MenuItemType.PAGE, "انتقال وجه", new UIPageDto(null, "moneyTransfer.xml"), new ArrayList<MenuItmDto>()))))

                ))))));
        return new ResponseDto(ResponseStatus.Ok, menuItmDto, null, null);

    }


    @RequestMapping(value = "/ws/uipage/getPage", method = RequestMethod.POST)
    public ResponseDto<String> getPage(@RequestParam String name) throws IOException {
        return new ResponseDto(ResponseStatus.Ok, readFile(name, StandardCharsets.UTF_8), null, null);

    }

    @RequestMapping(value = "/ws/deposit", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> deposit(@RequestBody MoneyTransferDto moneyTransferDto) {

        AccountTransaction  accountTransaction=new AccountTransaction();
        List<AccountTransaction>   accountTransactionList=new ArrayList<>();
        log.info(("call get account"));
        Account accountSource = accountDao.getAccountByNumber(moneyTransferDto.getSource());

        if (Objects.isNull((accountSource)) || Objects.isNull(moneyTransferDto.getAmount()))
        return new ResponseDto(ResponseStatus.Error, "", "",
                new ResponseException("اطلاعات کامل نمی باشد"));

        accountSource.setAmount(accountSource.getAmount().add(moneyTransferDto.getAmount()));

        accountTransaction.setAccountTransferType(AccountTransferType.DEPOSIT);
        accountTransaction.setAmount(accountSource.getAmount());
        accountTransaction.setTransactionDate(new Date());
        accountTransactionList.add(accountTransaction);



        accountSource.setAccountTransactionList(accountTransactionList);
       // accountDao.save(accountSource);



        return new ResponseDto(ResponseStatus.Ok, null, "وجه واریز شد", null);
    }

    @RequestMapping(value = "/ws/take", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> take(@RequestBody MoneyTransferDto moneyTransferDto) {
        AccountTransaction  accountTransaction=new AccountTransaction();
       List<AccountTransaction>   accountTransactionList=new ArrayList<>();
        Account accountSource = accountDao.getAccountByNumber(moneyTransferDto.getSource());
        if (Objects.isNull((accountSource)) || Objects.isNull(moneyTransferDto.getAmount()))
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نمی باشد"));

        if (moneyTransferDto.getAmount().compareTo(accountSource.getAmount()) > 0 || moneyTransferDto.getAmount().compareTo(BigDecimal.ZERO) < 0)
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("موجودی کافی نمی باشد"));

        accountSource.setAmount(accountSource.getAmount().subtract(moneyTransferDto.getAmount()));

        accountTransaction.setAccountTransferType(AccountTransferType.TAKE);
        accountTransaction.setAmount(accountSource.getAmount());
        accountTransaction.setTransactionDate(new Date());
        accountTransactionList.add(accountTransaction);



        accountSource.setAccountTransactionList(accountTransactionList);
       // accountDao.save(accountSource);



        return new ResponseDto(ResponseStatus.Ok, null, "وجه برداشت شد", null);
    }

    @RequestMapping(value = "/ws/transfer", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> transfer(@RequestBody MoneyTransferDto moneyTransferDto) {

        AccountTransaction  accountTransaction=new AccountTransaction();
        List<AccountTransaction>   accountTransactionList=new ArrayList<>();

        Account accountSource = accountDao.getAccountByNumber(moneyTransferDto.getSource());
        Account accountDestination = accountDao.getAccountByNumber(moneyTransferDto.getDestination());

        if (Objects.isNull(accountDestination) || Objects.isNull(accountSource) || Objects.isNull(moneyTransferDto.getAmount())) {
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نمی باشد"));
        }
        if (moneyTransferDto.getAmount().compareTo(accountSource.getAmount()) > 0 || moneyTransferDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("موجودی کافی نمی باشد"));
        }
        accountSource.setAmount(accountSource.getAmount().subtract(moneyTransferDto.getAmount()));

        accountTransaction.setAccountTransferType(AccountTransferType.TAKE);
        accountTransaction.setAmount(accountSource.getAmount());
        accountTransaction.setTransactionDate(new Date());
        accountTransactionList.add(accountTransaction);



        accountSource.setAccountTransactionList(accountTransactionList);
        accountDao.save(accountSource);

        accountDestination.setAmount(accountDestination.getAmount().add(moneyTransferDto.getAmount()));

        accountTransaction.setAccountTransferType(AccountTransferType.DEPOSIT);
        accountTransaction.setAmount(accountSource.getAmount());
        accountTransaction.setTransactionDate(new Date());
        accountTransactionList.add(accountTransaction);



        accountSource.setAccountTransactionList(accountTransactionList);
       // accountDao.save(accountDestination);

        return new ResponseDto(ResponseStatus.Ok, null, "وجه منتقل شد", null);
    }


    @RequestMapping(value = "/ws/addLegal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> saveLegal(@RequestBody LegalCostumer legalCostumer) {
        if (Objects.isNull(legalCostumer.getName()) || Objects.isNull(legalCostumer.getCompanyNumber()) || Objects.isNull(legalCostumer.getAccount()) ||
                Objects.isNull(legalCostumer.getAccount()))
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نمی باشد"));
        for (Account account : legalCostumer.getAccount()) {
            if (account.getAccountNumber().length() < 16 && account.getAccountNumber().length() > 19)
                return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("شماره کارت اشتباه می باشد"));
        }
        legalCostumer.setIsDeleted(1);
        legalCostumerDao.save(legalCostumer);
        return new ResponseDto(ResponseStatus.Ok, legalCostumer.getCompanyNumber(), "اطلاعات ذخیره شد.", null);
    }

    @RequestMapping(value = "/ws/addReal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> save(@RequestBody RealCostumer realCostumer) {
        if (Objects.isNull(realCostumer.getName()) || Objects.isNull(realCostumer.getLastName())
                || Objects.isNull(realCostumer.getNationalCode()))
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نمی باشد"));
        if (realCostumer.getNationalCode().length() < 10)
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("کد ملی اشتباه می باشد"));
        realCostumer.setIsDeleted(1);
        realCostumerDao.save(realCostumer);

        return new ResponseDto(ResponseStatus.Ok, "", "اطلاعات ذخیره شد.", null);
    }

    @RequestMapping(value = "/ws/findLegalByCompanyNumber", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<LegalCostumer> findLegalByCompanyNumber(@RequestParam String companyNumber) {
        LegalCostumer legalCostumer = legalCostumerDao.findLegalByCompanyNumber(companyNumber);
        if (Objects.isNull(legalCostumer))
            return new ResponseDto<>(ResponseStatus.Error, null, null, new ResponseException("اطلاعات یافت نشد"));
        return new ResponseDto<>(ResponseStatus.Ok, legalCostumer, null, null);
    }


    @RequestMapping(value = "/ws/findLegal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<List<LegalCostumer>> findLegal(@RequestBody SearchLegalDto searchLegalDto) {
        List<LegalCostumer> legalCostumerList = legalCostumerDao.findLegal(searchLegalDto.getName(), searchLegalDto.getCompanyTpe());
        if (Objects.isNull(searchLegalDto.getName()) && Objects.isNull(searchLegalDto.getCompanyTpe())) {
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نیست"));
        }

        return new ResponseDto(ResponseStatus.Ok, legalCostumerList, "", null);

    }

    @RequestMapping(value = "/ws/findRealByNationalCode", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<RealCostumer> findRealByNationalCode(@RequestParam String nationalCode) throws Exception{
        RealCostumer realCostumer = realCostumerDao.findRealByNationalCode(nationalCode);
        if (Objects.isNull(realCostumer))
           // return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات یافت نشد"));
            throw new Exception("اطلاعات یافت نشد");
        return new ResponseDto(ResponseStatus.Ok, realCostumer, "", null);

    }

    @RequestMapping(value = "/ws/findReal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<List<RealCostumer>> findReal(@RequestBody SearchRealDto searchRealDto) {
        List<RealCostumer> realCostumer = realCostumerDao.findReal(searchRealDto.getName(), searchRealDto.getLastName());
        if (Objects.isNull(searchRealDto.getName()) && Objects.isNull(searchRealDto.getLastName())) {
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات یافت نشد"));
        }
        return new ResponseDto(ResponseStatus.Ok, realCostumer, "", null);
    }

    @RequestMapping(value = "/ws/getAccountByNumber", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<Account> getAccountByNumber(@RequestParam String accountNumber) {
        Account account = accountDao.getAccountByNumber(accountNumber);
        if (Objects.isNull(account))
            return new ResponseDto(ResponseStatus.Error, "", "",
                    new ResponseException("اطلاعات یافت نشد"));
        return new ResponseDto(ResponseStatus.Ok, account, "", null);

    }


    @RequestMapping(value = "/ws/deleteReal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> deleteReal(@RequestBody RealCostumer realCostumer) throws Exception {
        realCostumer.setIsDeleted(0);
        try {
            realCostumerDao.save(realCostumer);
            return new ResponseDto(ResponseStatus.Ok, "", "حذف با موفقیت انجام شد", null);

        }catch ( Throwable e){
            throw new Exception("no delete",e);
        }
    }

    @RequestMapping(value = "/ws/deleteLegal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> deleteLegal(@RequestBody LegalCostumer legalCostumer) {
        legalCostumer.setIsDeleted(0);
        legalCostumerDao.save(legalCostumer);
        return new ResponseDto(ResponseStatus.Ok, "", "حذف با موفقیت انجام شد", null);

    }


    @Transactional(rollbackOn = Exception.class)
    @RequestMapping(value = "/ws/benefit", method = RequestMethod.POST)
    //@Scheduled(cron = "0 0 0 * * ?") //Every day at midnight - 12am
    //@Scheduled(cron = "0 0/1 * 1/1 * ? *")
    public void calculateDaily() {
        List<Account> accountList = accountDao.getAllAccount();
        BigDecimal minAmount;
        for (Account account : accountList) {
            if(account.getAccountTransactionList().size()!=0) {
                 minAmount = account.getAccountTransactionList().stream()
                        .map(AccountTransaction::getAmount)
                        .min(Comparator.naturalOrder())
                        .orElse(BigDecimal.ZERO);
            }else {
                minAmount=account.getAmount();
            }
          if(account.getBenefit()!=null)
            account.setBenefit((minAmount.multiply(new BigDecimal(account.getRate() / 36500))).add(account.getBenefit()));//.multiply(new BigDecimal(30))
         else
            account.setBenefit((minAmount.multiply(new BigDecimal(account.getRate() / 36500))));//.multiply(new BigDecimal(30))

        }

    }

    @Transactional(rollbackOn = Exception.class)
    @RequestMapping(value = "/ws/calculate", method = RequestMethod.POST)
    //@Scheduled(cron = "0 0 12 1 * ?") //Every month on the 1st, at noon
    //@Scheduled(cron = "0 0 12 1 * ?")
    public void calculateIntrest() {
        List<Account> accountList = accountDao.getAllAccount();
        for (Account account : accountList) {
            account.setAmount(account.getAmount().add(account.getBenefit()));

        }

    }

    String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(new ClassPathResource(path).getFile().getPath()));
        return new String(encoded, encoding);
    }
}



