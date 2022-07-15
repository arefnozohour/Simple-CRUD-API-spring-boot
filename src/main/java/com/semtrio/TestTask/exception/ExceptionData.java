package com.semtrio.TestTask.exception;

public enum ExceptionData {
    user_NOTFOUND("user not found",1),
    username_UNIQUE("username already exists",2),
    email_UNIQUE("email already exists",3),
    phone_UNIQUE("phone already exists",4),
    post_NOTFOUND("post not found",5),
    comment_NOTFOUND("comment not found",6),
    album_NOTFOUND("album not found",7),
    photo_NOTFOUND("photo not found",8),
    todo_NOTFOUND("todo not found",9),


    OTPCode_WRONG("wrong OTP code",2),
    OTPCode_EXPIRED("OTPCode expired",3),
    mobile_BlOCKED("mobile is block",4),
    category_NOTFOUND("category not found",5),
    category_CANTDELETE("category cant delete",6),
    tag_category_NOTFOUND("tagCategory not found",7),
    tag_category_CANTDELETE("tagCategory cant delete",8),
    tag_NOTFOUND("tag not found",9),
    tag_CANTDELETE("ta cant delete",10),
    product_NOTFOUND("product not found",11),
    phoneOrPassword_WRONG("phone or password is wong",12),
    phoneOrOTP_WRONG("phone or otp is wong",12),
    productMedia_NOTFOUND("productMedia not found",13),
    phone_EXIST("phone is exist",14),
    basket_NOTFOUND("basket not found",15),
    city_NOTFOUND("city not found",16),
    state_NOTFOUND("state not found",17),
    address_NOTFOUND("address not found",18),
    discount_NOTFOUND("discount not found",19),
    product_serialId_EXIST("product serialId is  exist",20),
    order_NOTFOUND("order not found",21),
    discount_CODEEXSITS("code exists",22),
    productComment_NOTFOUND("productComment not found",23 ),
    campaign_NOTFOUND("campaign not found",24 ),
    invalid_ERROR("invalid error",500 );

    public final String message;
    public final Integer number;

    ExceptionData(String message, Integer number) {
        this.message = message;
        this.number = number;
    }

    public static ExceptionData get(String name)
    {
        name=name.toLowerCase();
        for (ExceptionData exceptionData:ExceptionData.values())
            if (exceptionData.name().toLowerCase().equals(name))
                return exceptionData;
        return null;
    }

}
