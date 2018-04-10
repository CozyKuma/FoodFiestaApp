package com.example.cozykuma.foodinventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;


/**
 * Created by CozyKuma on 04-04-2018.
 */

public class FoodItem {

    enum sortTypes {
        DAYSLEFT, DATEADDED, NAME
    }

    enum filterTypes {
        OPEN, EXPIRED, NOTIFY, NOT_OPEN, NOT_EXPIRED, NOT_NOTIFY
    }

    private static ArrayList<FoodItem> listOfItems = new ArrayList<FoodItem>();
    private org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
    private String itemName;
    private static int countId = 0;
    static private String sortType = "Expire Date";
    private int itemId;
    private Date dateAdded;
    private Date dateExpire;
    private Date dateOpened;
    private boolean expired;
    private boolean opened;
    private boolean used;
    private boolean notifyMe;
    private FoodCategory category;
    private int daysLeft;

    FoodItem(String itemName, String dateExpire) {
        this.itemName = itemName;
        this.itemId = countId;
        this.expired = false;
        this.opened = false;
        this.used = false;
        this.dateAdded = new Date();
        //System.out.println(dtf.parseDateTime(dateExpire));
        this.dateExpire = dtf.parseDateTime(dateExpire).toDate();
        this.notifyMe = true;
        countId++;
        daysLeft = daysBetween(new Date(), this.dateExpire);
        listOfItems.add(this);
        }

    FoodItem(String itemName, Date dateExpire) {
        this.itemName = itemName;
        this.itemId = countId;
        this.expired = false;
        this.opened = false;
        this.used = false;
        this.dateAdded = new Date();
        this.dateExpire = dateExpire;
        this.notifyMe = true;
        countId++;
        daysLeft = daysBetween(new Date(), this.dateExpire);
        listOfItems.add(this);
    }

    public static ArrayList<FoodItem> getListOfItems() {
        return listOfItems;
    }

    public static void addItem(String itemName, Date dateExpire) {
        FoodItem newFoodItem = new FoodItem(itemName, dateExpire);
        //listOfItems.add(newFoodItem);
    }

    public static void addItem(String itemName, String dateExpire) {
        FoodItem newFoodItem = new FoodItem(itemName, dateExpire);
        //listOfItems.add(newFoodItem);
    }

    public static int daysBetween(Date d1, Date d2) {
        return Days.daysBetween(
                new org.joda.time.LocalDate(d1.getTime()),
                new org.joda.time.LocalDate(d2.getTime())).getDays();
    }

    public String getItemName() {
        return itemName;
    }

    public void setCategory(FoodCategory category) { this.category = category; }

    public int getItemId() {
        return itemId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public Date getDateExpire() {
        return dateExpire;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public int getDatesLeft() {
        return daysLeft;
    }

    public boolean isExpired() {
        return expired;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isNotifyMe() {
        return notifyMe;
    }

}
