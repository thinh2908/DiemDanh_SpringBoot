package com.diemdanh.base;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageString {
    public static String SUCCESS_CREATE_EMPLOYEE() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Một nhân viên đã được bạn tạo" +
                ": ";
    } ;
    public static String SUCCESS_UPDATE_EMPLOYEE() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Một nhân viên đã được bạn cập nhật" +
                ": ";
    } ;
    public static String SUCCESS_DELETE_EMPLOYEE() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Một nhân viên đã được bạn xoá" +
                ": ";
    } ;
    public static String SUCCESS_CREATE_USER() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Một tài khoản đã được bạn tạo" +
                ": ";
    } ;
    public static String SUCCESS_UPDATE_USER() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Một tài khoản đã được bạn cập nhật" +
                ": ";
    }
    public static String SUCCESS_DELETE_USER() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Một tài khoản đã được bạn xoá" +
                ": ";
    }
    public static String SUCCESS_ATTENDANCE() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Đã điểm danh thành công";
    } ;

    public static String SUCCESS_APPROVAL_LEAVING() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Bạn đã chấp nhận yêu cầu nghỉ của user:  ";
    } ;

    public static String SUCCESS_REJECT_LEAVING() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Bạn đã từ chối yêu cầu nghỉ của user: ";
    } ;

    public static String APPROVAL_LEAVING() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Bạn đã được chấp nhận yêu cầu nghỉ bời : ";
    } ;

    public static String REJECT_LEAVING() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd/MM/yyyy"))
                + ": Bạn đã bị từ chối yêu cầu nghỉ bời : ";
    } ;

}
