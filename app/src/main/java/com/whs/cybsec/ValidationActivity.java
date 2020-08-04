package com.whs.cybsec;

import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * @author Satvik Dasari
 *  Validation class for all field validations
 */
public class ValidationActivity {

    // Error Messages
    public static final String REQUIRED_MSG = "Value required";
    public static final String HYPHEN = "-";
    //Regular expressions
    public static final String REGEX_USER_ID =
            "^(?![@_0-9].*)(?=.*[a-zA-Z])(?!.*[^a-zA-Z0-9@_-]).{8,20}$";
    public static final String REGEX_PASSWORD =
            "((?=.*\\d)(?=.*[A-Z])(?=.*[!@#$%^&*()_+/?=-])(?=\\S+$).{8,20})";
    // validating userId
    public static final String REGEX_LAST_NAME =
            "^(([0-9A-Za-z\\s\\.'\\-]){1,35})$";
    public static final String REGEX_FIRST_NAME =
            "^(([0-9A-Za-z\\s\\.'\\-]){1,35})$";
    public static final String REGEX_PHONE =
            "\\d{10,14}||\\d{3}-\\d{3}-\\d{4}||\\d{3}-\\d{3}-\\d{4}\\s(x)\\d{1,4}";
    public static final String PHONE_SAME_DIGITS = "^(\\d)\\1*$";
    public static final String REGEX_US_ZIP = "^(\\d){5}||(\\d){9}$";
    public static final String REGEX_CITY = "^([a-zA-Z0-9\\.\\s,\\-]){1,24}$";
    // Regular Expression
    public static final String REGEX_GRADE = "(9|10|11|12)";
    public static final String REGEX_EMAIL =
            "^[_A-Za-z0-9-]+(\\." + "[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*" +
                    "(\\.[A-Za-z]{2,})$";


    public static boolean isValidUserId(EditText editText) {
        return isValid(editText, REGEX_USER_ID);
    }
    // validating password
    public static boolean isValidPassword(String pass) {
        return isValidValue(pass, REGEX_PASSWORD);
    }
    //firstname validation
    public static boolean isFirstNameValid(EditText editText) {
        return isValid(editText, REGEX_FIRST_NAME);
    }
    //lastname validation
    public static boolean isLastNameValid(EditText editText) {
        return isValid(editText, REGEX_LAST_NAME);
    }
    //email validation
    public static boolean isEmailAddressValid(EditText editText) {
        return isValid(editText, REGEX_EMAIL);
    }

    //phone number validation
    public static boolean isPhoneNumberValid(EditText editText) {
        boolean valid = true;
        if(!editText.getText().toString().isEmpty()) {
            String phoneNum = editText.getText().toString();
            if (!phoneNum.isEmpty()&&
                    (phoneNum.startsWith("000") || phoneNum.matches(PHONE_SAME_DIGITS))) {
                valid = false;
            }
        }
        return valid;
    }

    public static boolean isPhoneFormatValid(EditText editText){
        boolean valid = true;
        if(!editText.getText().toString().isEmpty()) {
            valid = isValid(editText, REGEX_PHONE);
        }
        return valid;
    }

    /**
     *  Validate Phone and return formatted Phone with "-"
     * @param phone
     */
    public static String getFormattedPhone(String phone){
        StringBuilder formattedPhone = null;
        if(!phone.isEmpty()) {
            phone = phone.replaceAll("\\-", "");
            if (phone.length() == 10 && phone.matches(REGEX_PHONE)) {
                formattedPhone = new StringBuilder();
                formattedPhone.append(phone.substring(0, 3));
                formattedPhone.append(HYPHEN);
                formattedPhone.append(phone.substring(3, 6));
                formattedPhone.append(HYPHEN);
                formattedPhone.append(phone.substring(6, 10));
                phone = formattedPhone.toString();
            }
        }
        return phone;
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex) {
        boolean valid = true;
        String text = editText.getText().toString().trim();
        // clearing the exception, if it was previously set by some other values
        editText.setError(null);
        // pattern doesn't match so returning false
        if (!Pattern.matches(regex, text)) {
            valid = false;
        };
        return valid;
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValidValue(String value, String regex) {
        boolean valid = true;
        String text = value.trim();
         // pattern doesn't match so returning false
        if (!Pattern.matches(regex, text)) {
            valid = false;
        };
        return valid;
    }
    // return true if the input field is valid, based on the parameters passed
    public static boolean isValidAndMsg(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the exception, if it was previously set by some other values
        editText.setError(null);
        //get the message value from message bundle if key is given.
//        String message = Utils.getMessageValue(errMsg);
        String message = errMsg;
        if (message == null) {
           message = errMsg;
        }
        // text required and editText is blank, so return false
        if ( required && !hasText(editText,message) ){
            return false;
        }
        // pattern doesn't match so returning false
        if (!regex.isEmpty()){
            if (required && !Pattern.matches(regex, text)) {
                editText.setError(message);
                return false;
            };
        }
        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasTextNoErrorSet(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            return false;
        }
        return true;
    }
    // check the input field has any text or not return true if it contains text otherwise false.
    // Also sets error to the field
    public static boolean hasText(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }
        return true;
    }

    // check the input field has any text or not return true if it contains text otherwise false.
    // Also sets error to the field
    public static boolean hasText(EditText editText, String errorMsg) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(errorMsg);
            return false;
        }
        return true;
    }
    // check the input field value and return true or false
    public static boolean hasValue(String value) {
        // length 0 means there is no value
        if (value == null || value.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Display error message to the user
     */
    public static void displayErrorMsg(String messageKey, TextView error){
        //get the message value from message bundle if key is given.
        String message = messageKey;
        if (message == null) {
            message = messageKey;
        }
        error.setText(message);
        error.setVisibility(TextView.VISIBLE);
    }

}