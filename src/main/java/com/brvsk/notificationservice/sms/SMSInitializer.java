package com.brvsk.notificationservice.sms;

import com.twilio.Twilio;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SMSInitializer {

    private final SMSConfiguration smsConfiguration;

    public SMSInitializer(SMSConfiguration smsConfiguration) {
        this.smsConfiguration = smsConfiguration;
        Twilio.init(
                smsConfiguration.getACCOUNT_SID(),
                smsConfiguration.getAUTH_TOKEN()
        );
    }


}
