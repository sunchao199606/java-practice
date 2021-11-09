package cn.com.sun.javaApi;

import org.junit.jupiter.api.Test;

/**
 * @Description : 字符串测试
 * @Author :sunchao
 * @Date: 2020-07-13 13:39
 */
public class StringTest {

    @Test
    public void add() {
//        String a = "a";
//        String b = "b";
//        String c = a + b;
//        System.out.println(c);
        int i = 8;
        float f = 3.0f;
        System.out.println((int) (i / f));
    }

    @Test
    public void test() {
        String a = "孙超;;;;;;;;;;;;;;;jjjjjjj";
        String b = "a";
        System.out.println(a);
        Class clazz = StringTest.class;
        clazz.getEnclosingMethod();
    }

    @Test
    public void testLength() {
        String s = "{\n" +
                "\t\"Ret\":\t\"<?xml version=\\\"1.0\\\" encoding=\\\"GBK\\\" ?>\\n<Transaction>\\n    <ESBHead>\\n        <RequesterId>011</RequesterId>\\n        <ResponderId>ECM001</ResponderId>\\n        <ESBServiceId />\\n        <MessageType>1</MessageType>\\n        <ESBReqTimestamp>20210727165153</ESBReqTimestamp>\\n        <ESBRspTimestamp>20210727165207</ESBRspTimestamp>\\n        <SuccessCode>0</SuccessCode>\\n        <PageNumber>1</PageNumber>\\n        <TotalImageNumer>2</TotalImageNumer>\\n        <TotalVoucherNumer>1</TotalVoucherNumer>\\n        <Version>1.0</Version>\\n        <Reserved>182.119.115.120</Reserved>\\n    </ESBHead>\\n    <ESBBody>\\n        <AppResponse>\\n            <AppRspHead>\\n                <ReqSerialNo />\\n                <TradeCode>CREATE</TradeCode>\\n                <StatusInfo>\\n                    <MsgType>N</MsgType>\\n                    <MsgCode>0</MsgCode>\\n                    <MsgInfo>Success</MsgInfo>\\n                </StatusInfo>\\n                <TradeDate>20111103</TradeDate>\\n                <TradeTime>100421</TradeTime>\\n                <Reserved />\\n            </AppRspHead>\\n            <AppRspBody>\\n                <CommonRsp>\\n                    <RequestLevel>1</RequestLevel>\\n                    <TotalSize>0</TotalSize>\\n                    <EventID>1958347951</EventID>\\n                    <EventType>1</EventType>\\n                    <ResponseSeq>2</ResponseSeq>\\n                </CommonRsp>\\n                <BatchIndex                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ";
        System.out.print(s.getBytes().length);
    }
}
