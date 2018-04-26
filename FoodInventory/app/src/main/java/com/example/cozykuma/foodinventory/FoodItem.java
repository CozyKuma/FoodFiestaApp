package com.example.cozykuma.foodinventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;


/**
 * Created by CozyKuma on 04-04-2018.
 */

public class FoodItem {

    enum sortTypes {
        DAYSLEFT, DATEADDED, NAME, PROGRESS, CATEGORY
    }

    enum filterTypes {
        OPEN, EXPIRED, NOTIFY, NOT_OPEN, NOT_EXPIRED, NOT_NOTIFY
    }

    private static ArrayList<FoodItem> listOfItems = new ArrayList<FoodItem>();
    private org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
    private String itemName;
    private static int countId = 0;
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
    private int amountLeft;

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
        this.amountLeft = 100;
        countId++;
        daysLeft = daysBetween(new Date(), this.dateExpire);
        listOfItems.add(this);
        }

    FoodItem(String itemName, String dateExpire, FoodCategory category) {
        this.itemName = itemName;
        this.itemId = countId;
        this.expired = false;
        this.opened = false;
        this.used = false;
        this.dateAdded = new Date();
        this.category = category;
        //System.out.println(dtf.parseDateTime(dateExpire));
        this.dateExpire = dtf.parseDateTime(dateExpire).toDate();
        this.notifyMe = true;
        this.amountLeft = 100;
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
        this.amountLeft = 100;
        countId++;
        daysLeft = daysBetween(new Date(), this.dateExpire);
        listOfItems.add(this);
    }

    public static ArrayList<FoodItem> getListOfItems() {
        return listOfItems;
    }

    public static ArrayList<FoodItem> sortList(sortTypes sort, ArrayList<FoodItem> list) {
        if (sort == sortTypes.DAYSLEFT) {
            Collections.sort(list, new Comparator<FoodItem>() {
                @Override
                public int compare(FoodItem o1, FoodItem o2) {
                    return o1.getDatesLeft() - o2.getDatesLeft();
                }
            });
        } else if (sort == sortTypes.DATEADDED) {
            Collections.sort(list, new Comparator<FoodItem>() {
                @Override
                public int compare(FoodItem o1, FoodItem o2) {
                    return daysBetween(o1.getDateAdded(), o2.getDateAdded());
                }
            });
        } else if (sort == sortTypes.NAME) {
            Collections.sort(list, new Comparator<FoodItem>() {
                @Override
                public int compare(FoodItem o1, FoodItem o2) {
                    return o1.getItemName().compareTo(o2.getItemName());
                }
            });
        } else if (sort == sortTypes.CATEGORY) {
            Collections.sort(list, new Comparator<FoodItem>() {
                @Override
                public int compare(FoodItem o1, FoodItem o2) {
                    return o1.getItemCategoryName().compareTo(o2.getItemCategoryName());
                }
            });
        }


        return list;
    }

    public static int daysBetween(Date d1, Date d2) {
        return Days.daysBetween(
                new org.joda.time.LocalDate(d1.getTime()),
                new org.joda.time.LocalDate(d2.getTime())).getDays();
    }

    public String getItemName() {
        return itemName;
    }

    public FoodCategory getCategory() { return category; }

    public String getItemCategoryName() { return category.getCategoryName(); }

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

    public static sortTypes getSortType() {
        return sortType;
    }

}
