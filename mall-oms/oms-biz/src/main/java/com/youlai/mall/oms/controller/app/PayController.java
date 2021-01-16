package com.youlai.mall.oms.controller.app;

import com.alibaba.fastjson.JSONObject;
import com.youlai.common.core.result.Result;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;


/**
 * 支付接口
 */
@RestController
@RequestMapping("/api.app/v1/pays")
public class PayController {


 /*   @RequestMapping(value = "onlinepay", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView onlinePay(HttpServletRequest request, String price, String name) {
        ModelAndView returnMV = new ModelAndView();
        String res = "";
        JSONObject jsonObject = null;
        //统一下单
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(ZZFHttpUtils.CreateOrderURL);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
        // 设置连接超时时间(单位毫秒)
        managerParams.setConnectionTimeout(5000);
        // 设置读数据超时时间(单位毫秒)
        managerParams.setSoTimeout(5000);
        // 头信息
        postMethod.addRequestHeader("Payment-Key", ZZFHttpUtils.AppKey);
        postMethod.addRequestHeader("Payment-Secret", ZZFHttpUtils.AppSecret);
        postMethod.addParameter("price", price);
        postMethod.addParameter("name", name);
        //postMethod.addParameter("callbackurl", "用来通知指定地址");
        //postMethod.addParameter("reurl", "跳转地址");
        //postMethod.addParameter("thirduid", "用户存放您的用户ID");
        //postMethod.addParameter("remarks", "备注");
        //postMethod.addParameter("other", "其他信息");
        try {
            System.out.println(postMethod.getURI());
            int code = client.executeMethod(postMethod);
            if (code == 200) {
                res = postMethod.getResponseBodyAsString();
                System.out.println(res);
                //解析站长付返回数据
                // 成功：{ "msg": "下单成功", "other": "", "code": "10001", "orderId": "oderpay-7ae379d1-e4c1-4acd-8d9a-584a208b28b7", "price": "99.13", "name": "开通超级VIP", "reurl": "", "thirduid": "15811111111", "originalprice ": "100", "remarks": "" }
                //失败：{ "msg": "下单失败，支付金额有误","code": "10002", }
                jsonObject = JSONObject.parseObject(res);
                if (jsonObject.getString("code").equals("10001")) {
                    //成功
                    request.setAttribute("name", jsonObject.getString("name"));
                    request.setAttribute("price", jsonObject.getString("price"));
                    request.setAttribute("orderId", jsonObject.getString("orderId"));
                } else {
                    //有误
                    request.setAttribute("name", jsonObject.getString("msg"));
                    request.setAttribute("price", jsonObject.getString("msg"));
                }
            }
        } catch (Exception e) {
            System.out.println("错误：" + e.getMessage());
            e.printStackTrace();
            request.setAttribute("name", e.getMessage());
            request.setAttribute("price", e.getMessage());
        }

        returnMV.setViewName("onlinepay");
        return returnMV;
    }

    *//**
     * 订单支付状态查询
     *//*
    @ResponseBody
    @RequestMapping(value = "findOrderState", method = {RequestMethod.GET, RequestMethod.POST})
    public Object findOrderState(String orderId) {
        String res = "";
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(ZZFHttpUtils.FindOrderURL);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
        // 设置连接超时时间(单位毫秒)
        managerParams.setConnectionTimeout(5000);
        // 设置读数据超时时间(单位毫秒)
        managerParams.setSoTimeout(5000);
        // 头信息
        postMethod.addRequestHeader("Payment-Key", ZZFHttpUtils.AppKey);
        postMethod.addRequestHeader("Payment-Secret", ZZFHttpUtils.AppSecret);
        postMethod.addParameter("orderId", orderId);
        try {
            System.out.println(postMethod.getURI());
            int code = client.executeMethod(postMethod);
            if (code == 200) {
                //解析站长付返回数据
                //  { "msg": "订单支付完成", "code": "10001", "state": "0" }
                res = postMethod.getResponseBodyAsString();
                System.out.println(res);
            }
        } catch (Exception e) {
            System.out.println("错误：" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject=JSONObject.parseObject(res);
        return jsonObject;
    }

    *//*
     *  接收站长付发送的通知,数据格式如下
     *   {"code": 10001,"msg": "回调成功","other": "","orderId": "oderpay-445b1306-6f50-48dd-99c1-9a704108f8ff","price": "99.97","originalprice":"100","name": "开通超级VIP","reurl": "","thirduid": "15811111111","paytype": "0","remarks": ""}
     *//*
    @ResponseBody
    @RequestMapping(value = "/receiveNotify", method = {RequestMethod.GET, RequestMethod.POST})
    public void receiveCallBack(@RequestBody ResultPayEntity resultPay) {

        System.out.println("receiveNotify：" + resultPay.toString());
        //处理自己的业务逻辑
        //例如开通会员、用户充值等等。。。
    }
    */

}
