package com.app.csec_otm.search;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents the set of information contained in one result row
 */
public class ResultItem implements Parcelable {

    private String header;
    private String subHeader;
    private String description;
    private Integer leftIcon;
    private Integer rightIcon;

    // Constructors ________________________________________________________________________________
    public ResultItem () {
        this.header = "Error";
        this.subHeader = "Error";
        this.leftIcon = com.app.csec_otm.R.drawable.clock_icon;
        this.rightIcon = com.app.csec_otm.R.drawable.arrow_left_up_icon;
    }

    public ResultItem (String header, String subHeader, Integer leftIcon, Integer rightIcon, String description) {
        this.setHeader(header);
        this.setSubHeader(subHeader);
        this.setLeftIcon(leftIcon);
        this.setRightIcon(rightIcon);
        this.setDescription(description);
    }

    // Getters and Setters__________________________________________________________________________
    public Integer getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(Integer leftIcon) {
        if (leftIcon != null && leftIcon != 0 && leftIcon != -1) {
            this.leftIcon = leftIcon;
        } else {
            this.leftIcon = com.app.csec_otm.R.drawable.clock_icon;
        }
    }

    public Integer getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(Integer rightIcon) {
        if (rightIcon != null && rightIcon != 0 && rightIcon != -1) {
            this.rightIcon = rightIcon;
        } else {
            this.rightIcon = com.app.csec_otm.R.drawable.arrow_left_up_icon;
        }
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        if (subHeader != null && !"".equals(subHeader)) {
            this.subHeader = subHeader;
        } else {
            this.subHeader = "Error (Empty data)";
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Parcelable contract implementation __________________________________________________________
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(header);
        dest.writeString(subHeader);
        dest.writeInt(leftIcon);
        dest.writeInt(rightIcon);
        dest.writeString(description);
    }

    // Parcelable Creator Implementation ___________________________________________________________
    public static final Creator<com.app.csec_otm.search.ResultItem> CREATOR = new Creator<com.app.csec_otm.search.ResultItem>() {

        public com.app.csec_otm.search.ResultItem createFromParcel(Parcel in) {
            com.app.csec_otm.search.ResultItem resultItem = new com.app.csec_otm.search.ResultItem();

            resultItem.setHeader(in.readString());
            resultItem.setSubHeader(in.readString());
            resultItem.setLeftIcon(in.readInt());
            resultItem.setRightIcon(in.readInt());
            resultItem.setDescription(in.readString());

            return resultItem;
        }

        public com.app.csec_otm.search.ResultItem[] newArray(int size) {
            return new com.app.csec_otm.search.ResultItem[size];
        }
    };
}
