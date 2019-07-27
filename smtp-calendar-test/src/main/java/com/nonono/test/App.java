package com.nonono.test;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SendCalendarHelper sendCalendarHelper = new SendCalendarHelper();
        try {
            //sendCalendarHelper.send();
            sendCalendarHelper.createEvent();
            //sendCalendarHelper.cancel("2ad1db7c-3a55-4634-a5c5-32d86c3e3917");
            System.out.println("测试发送日程结束。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
