package com.sharkit.stft.settings;

import android.content.Context;
import android.text.TextUtils;

import com.sharkit.stft.Notification.ToastComplete;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean isValidAdminRegistration(String email, String name, String password, Context context){
        if (TextUtils.isEmpty(email)){
            try {
                throw new ToastComplete(context,"Введіть пошту");
            } catch (ToastComplete toastComplete) {
                toastComplete.printStackTrace();
            }
            return false;
        }
        if (!isValidEmail(email)){
            try {
                throw new ToastComplete(context, "Введіть правильний формат пошти");
            } catch (ToastComplete toastComplete) {
                toastComplete.printStackTrace();
            }
            return false;
        }

        if (TextUtils.isEmpty(name)){
            try {
                throw new ToastComplete(context, "Введить назву фірми");
            } catch (ToastComplete toastComplete) {
                toastComplete.printStackTrace();
            }
            return false;
        }
        if (isValidatorName(name)){
            try {
                throw new ToastComplete(context, "Назва фірми не повина містити симфолів");
            }catch (ToastComplete toastComplete){
                toastComplete.printStackTrace();
            }
            return false;
        }

        if (password.trim().length() < 6){
            try {
                throw new ToastComplete(context, "Пароль повинен містити більше 5 символів");
            } catch (ToastComplete toastComplete) {
                toastComplete.printStackTrace();
            }
            return false;
        }
        if (ValidatorPass(password)){
            try {
                throw new ToastComplete(context, "Пароль не повинен містити кириллиці або пробілів");
            } catch (ToastComplete toastComplete) {
                toastComplete.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public static boolean isValidatorName(String s){
        Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>?{}\\[\\]~×÷/€£¥₴^\";,`]°•○●□■♤♡◇♧☆▪¤《》¡¿,.]");
        Matcher hasSigns = sign.matcher(s);
        return (hasSigns.find());
    }

    private static boolean ValidatorPass(String s){
        Pattern cyrillic = Pattern.compile("[а-яА-Я]");
        Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>?{}\\[\\]~×÷/€£¥₴^\";,`]°•○●□■♤♡◇♧☆▪¤《》¡¿,.]");
        Pattern space = Pattern.compile(" ");
        Matcher hasSign = sign.matcher(s);
        Matcher hasCyrillic = cyrillic.matcher(s);
        Matcher hasSpace = space.matcher(s);
        return  (hasCyrillic.find() || hasSpace.find() || hasSign.find());
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
