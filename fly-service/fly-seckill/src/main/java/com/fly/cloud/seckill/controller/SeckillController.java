package com.fly.seckill.controller;


import com.fly.seckill.enums.SeckillStatEnum;
import com.fly.seckill.exception.RepeatKillException;
import com.fly.seckill.exception.SeckillCloseException;
import com.fly.seckill.exception.SeckillException;
import com.fly.seckill.pojo.dto.Exposer;
import com.fly.seckill.pojo.dto.SeckillExecution;
import com.fly.seckill.pojo.dto.SeckillResult;
import com.fly.seckill.pojo.entity.Seckill;
import com.fly.seckill.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private ISeckillService iSeckillService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/list")
    public String findSeckillList(Model model) {
        List<Seckill> list = iSeckillService.findAll();
        model.addAttribute("list", list);
        return "page/seckill";
    }

    @ResponseBody
    @RequestMapping("/findById")
    public Seckill findById(@RequestParam("id") Long id) {
        return iSeckillService.findById(id);
    }

    @RequestMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "page/seckill";
        }
        Seckill seckill = iSeckillService.findById(seckillId);
        model.addAttribute("seckill", seckill);
        if (seckill == null) {
            return "page/seckill";
        }
        return "page/seckill_detail";
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = iSeckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @RequestParam("money") BigDecimal money,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        try {
            SeckillExecution execution = iSeckillService.executeSeckill(seckillId, money, userPhone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillCloseException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        }
    }

    @ResponseBody
    @GetMapping(value = "/time/now")
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult(true, now.getTime());
    }
}
