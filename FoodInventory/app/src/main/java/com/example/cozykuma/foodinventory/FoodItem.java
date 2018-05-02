package com.example.cozykuma.foodinventory;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

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
    private static sortTypes sortType = sortTypes.DAYSLEFT;
    private Date dateAdded;
    private Date dateExpire;
    private Date dateOpened;
    private boolean expired;
    private boolean opened;
    private boolean used;
    private boolean notifyMe;
    private static boolean notifySetting = true;
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
        this.dateExpire = dtf.parseDateTime(dateExpire).toDate();
        this.notifyMe = notifySetting;
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
        this.dateExpire = dtf.parseDateTime(dateExpire).toDate();
        this.notifyMe = notifySetting;
        this.amountLeft = 100;
        countId++;
        daysLeft = daysBetween(new Date(), this.dateExpire);
        listOfItems.add(this);
    }

    FoodItem(String itemName, String dateExpire, FoodCategory category, boolean notify, boolean open) {
        this.itemName = itemName;
        this.itemId = countId;
        this.expired = false;
        this.opened = open;
        this.used = false;
        this.dateAdded = new Date();
        this.category = category;
        this.dateExpire = dtf.parseDateTime(dateExpire).toDate();
        this.notifyMe = notify;
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
        this.notifyMe = notifySetting;
        this.amountLeft = 100;
        countId++;
        daysLeft = daysBetween(new Date(), this.dateExpire);
        listOfItems.add(this);
    }

    FoodItem(String itemName, FoodCategory category) {
        this.itemName = itemName;
        this.category = category;
        this.itemId = countId;
        this.expired = false;
        this.opened = false;
        this.used = false;
        this.dateAdded = new Date();
        this.dateExpire = dtf.parseDateTime(addDatePreset(category)).toDate();
        this.notifyMe = notifySetting;
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
        } else if (sort == sortTypes.PROGRESS) {
            Collections.sort(list, new Comparator<FoodItem>() {
                @Override
                public int compare(FoodItem o1, FoodItem o2) {
                    return o1.getAmountLeft() - o2.getAmountLeft();
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

    public FoodCategory getCategory() {
        return category;
    }

    public String getItemCategoryName() {
        return category.getCategoryName();
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public void setItemName(String name) {this.itemName = name;}

    public void setOpened(Boolean opened){this.opened = opened;}

    public void setNotifyMe(Boolean notifyMe){this.notifyMe = notifyMe;}

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

    public static void setSortType(sortTypes st) {
        sortType = st;
    }

    public int getAmountLeft() {
        return this.amountLeft;
    }

    public void setAmountLeft(int amountLeft) {this.amountLeft = amountLeft; }

    public void useAmount(int amountUsed) {
        this.amountLeft = this.amountLeft - amountUsed;
    }

    public static boolean getNotifySetting() {
        return notifySetting;
    }

    public static void setNotifySetting(boolean bool, Context context) {
        notifySetting = bool;
        String toastText;
        if (notifySetting) {
            toastText = "Items will now be set to notify you per default.";
        } else {
            toastText = "Items will no longer be set to notify you per default.";
        }
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setDateExpire(Date date) {
        this.dateExpire = date;
    }

    public void setDateExpire(String dateString) {
        Date date = dtf.parseDateTime(dateString).toDate();
        setDateExpire(date);
    }

    public static String addDatePreset(FoodCategory category) {
        Date today = new Date();
        DateTime jodaToday = new DateTime(today);
        jodaToday = jodaToday.plusDays(category.getDatePreset());
        String dateString = jodaToday.getDayOfMonth() + "-" + jodaToday.getMonthOfYear() + "-" + jodaToday.getYear();

        return dateString;
    }
}
