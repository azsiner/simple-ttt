package com.siner.ttt.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

/**
 * Handler internationalization of messages
 *
 * @author asiner
 */

@Component
public class MsgHandler implements MessageSourceAware
{
    private static MessageSource msgSource;

    @Override
    public void setMessageSource(MessageSource msgSource)
    {
        MsgHandler.msgSource = msgSource;
    }

    /**
     * Resolves a simple message (no parameters)
     *
     * @param label label from a message*.properties file
     * @return the internationalized version of text
     */
    public static String msg(String label) {
        return msg(label, (Object[]) null);
    }

    /**
     * Resolves a message that has parameters
     *
     * @param label label from a message*.properties file
     * @return the internationalized version of text, with the parameters filled in
     */
    public static String msg(String label, Object... params) {
        String defaultMsg = "???" + label + "???";
        String msg = msgSource.getMessage(label, params, defaultMsg, Locale.getDefault());
        return msg;

    }
}
