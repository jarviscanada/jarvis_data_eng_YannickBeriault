package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import ca.jrvs.apps.trading.service.TraderAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("/trader")
public class TraderAccountController {

    private TraderAccountService traderAccountService;

    @Autowired
    public TraderAccountController(TraderAccountService traderAccountService) {
        this.traderAccountService = traderAccountService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(path =
            "/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public TraderAccountView createTrader(@PathVariable String firstname,
                                          @PathVariable String lastname,
                                          @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")
                                              LocalDate dob,
                                          @PathVariable String country,
                                          @PathVariable String email) {

        try {

            Trader nuTrader = new Trader();
            nuTrader.setFirst_name(firstname);
            nuTrader.setLast_name(lastname);
            nuTrader.setDob(Date.valueOf(dob));
            nuTrader.setCountry(country);
            nuTrader.setEmail(email);

            return traderAccountService.createTraderAndAccount(nuTrader);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @DeleteMapping(path = "/traderid/{traderid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrader(@PathVariable Integer traderid) {

        try {
            traderAccountService.deleteTraderById(traderid);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/deposit/traderid/{traderid}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account depositFunds(@PathVariable Integer traderid,
                                @PathVariable Double amount) {

        try {
            return traderAccountService.deposit(traderid, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/withdraw/traderid/{traderid}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account withdrawFunds(@PathVariable Integer traderid,
                                 @PathVariable Double amount) {

        try {
            return traderAccountService.withdraw(traderid, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}