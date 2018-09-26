package com.wedul.wedulpos.user.dto;

/**
 * wedulpos
 *
 * @author wedul
 * @since 2018. 8. 21.
 **/
public enum EnumLoginType {
    NORMAL(0),
    FACE_BOOK(1);

    int flag;

    EnumLoginType(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
