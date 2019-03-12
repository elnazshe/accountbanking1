package com.example.accountbanking.controler;

import com.example.accountbanking.dao.AccountDao;
import com.example.accountbanking.dao.LegalCostumerDao;
import com.example.accountbanking.dao.RealCostumerDao;
import com.example.accountbanking.dto.*;
import com.example.accountbanking.dto.ResponseStatus;
import com.example.accountbanking.entity.Account;
import com.example.accountbanking.entity.LegalCostumer;
import com.example.accountbanking.entity.RealCostumer;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
public class ServiceController {

    //  @Autowired
    private LegalCostumerDao legalCostumerDao;
    private RealCostumerDao realCostumerDao;
    private AccountDao accountDao;

    public ServiceController(LegalCostumerDao legalCostumerDao, RealCostumerDao realCostumerDao, AccountDao accountDao) {
        this.legalCostumerDao = legalCostumerDao;
        this.realCostumerDao = realCostumerDao;
        this.accountDao = accountDao;
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
        Account accountSource =accountDao.getAccountByNumber(moneyTransferDto.getSource());
        accountSource.setAmount(accountSource.getAmount().add(moneyTransferDto.getAmount()));
        accountDao.save(accountSource);
        if (Objects.isNull((accountSource)) || Objects.isNull(moneyTransferDto))
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نمی باشد"));

        return new ResponseDto(ResponseStatus.Ok, null, "وجه واریز شد", null);
    }

    @RequestMapping(value = "/ws/take", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> take(@RequestBody MoneyTransferDto moneyTransferDto) {
        Account accountSource =accountDao.getAccountByNumber(moneyTransferDto.getSource());
        accountSource.setAmount(accountSource.getAmount().subtract(moneyTransferDto.getAmount()));
        accountDao.save(accountSource);
        if (Objects.isNull((accountSource)) || Objects.isNull(moneyTransferDto))
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نمی باشد"));
        if (moneyTransferDto.getAmount().compareTo(accountSource.getAmount()) > 0 || moneyTransferDto.getAmount().compareTo(BigDecimal.ZERO) < 0)
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("موجودی کافی نمی باشد"));

            return new ResponseDto(ResponseStatus.Ok, null, "وجه برداشت شد", null);
    }

    @RequestMapping(value = "/ws/transfer", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> transfer(@RequestBody MoneyTransferDto moneyTransferDto) {
        Account accountSource = accountDao.getAccountByNumber(moneyTransferDto.getSource());
        accountSource.setAmount(accountSource.getAmount().subtract(moneyTransferDto.getAmount()));
        accountDao.save(accountSource);
        Account accountDestination = accountDao.getAccountByNumber(moneyTransferDto.getDestination());
        accountDestination.setAmount(accountDestination.getAmount().add(moneyTransferDto.getAmount()));
        accountDao.save(accountDestination);
        if (Objects.isNull(accountDestination) || Objects.isNull(accountSource) || Objects.isNull(moneyTransferDto)) {
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نمی باشد"));
        }
        if (moneyTransferDto.getAmount().compareTo(accountSource.getAmount()) > 0 || moneyTransferDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("موجودی کافی نمی باشد"));
        }
        return new ResponseDto(ResponseStatus.Ok, null, "وجه منتقل شد", null);
    }


    @RequestMapping(value = "/ws/addLegal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> saveLegal(@RequestBody LegalCostumer legalCostumer) {
        if (Objects.isNull(legalCostumer.getName()) || Objects.isNull(legalCostumer.getCompanyNumber()) || Objects.isNull(legalCostumer.getAccount()) ||
                Objects.isNull(legalCostumer.getAccount()))
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نمی باشد"));
        for (Account account : legalCostumer.getAccount()) {
            if (account.getAccountNumber().length()<16 && account.getAccountNumber().length()> 19)
               return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("شماره کارت اشتباه می باشد"));
        }
            legalCostumerDao.save(legalCostumer);
        return new ResponseDto(ResponseStatus.Ok, legalCostumer.getCompanyNumber(), "اطلاعات ذخیره شد.", null);
    }

    @RequestMapping(value = "/ws/addReal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> save(@RequestBody RealCostumer realCostumer) {
        if (Objects.isNull(realCostumer.getName()) || Objects.isNull(realCostumer.getLastName())
                || Objects.isNull(realCostumer.getNationalCode()))
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات کامل نمی باشد"));
        if (realCostumer.getNationalCode().length()<16)
            return  new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("کد ملی اشتباه می باشد"));
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
    public ResponseDto<RealCostumer> findRealByNationalCode(@RequestParam String nationalCode) {
        RealCostumer realCostumer = realCostumerDao.findRealByNationalCode(nationalCode);
        if (Objects.isNull(realCostumer))
            return new ResponseDto(ResponseStatus.Error, "", "", new ResponseException("اطلاعات یافت نشد"));
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


    @Scheduled(cron = "0 0 0 * * ?") //Every day at midnight - 12am
    public void calculateDaily() {
        List<Account> accountList = accountDao.getAllAccount();
        for (Account account : accountList) {
            account.setBenefit((account.getAmount().multiply(new BigDecimal(account.getRate())).divide(new BigDecimal(365))).add(account.getBenefit()));//.multiply(new BigDecimal(30))
            accountDao.save(account);
        }


    }

    @Scheduled(cron = "0 0 12 1 * ?") //Every month on the 1st, at noon

    public void calculate() {
        List<Account> accountList = accountDao.getAllAccount();
        for (Account account : accountList) {
            account.setBenefit(account.getAmount().add(account.getBenefit()));
            accountDao.save(account);
        }


    }


    String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(new ClassPathResource(path).getFile().getPath()));
        return new String(encoded, encoding);
    }
}



