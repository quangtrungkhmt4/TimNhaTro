package com.example.quang.timnhatro.object;

public class ItemHouse {
    private int id;
    private String title;
    private String address;
    private String image;
    private String desc;
    private String contact;
    private float acreage;
    private float price;
    private int idUser;

    public ItemHouse(int id, String title, String address, String image, String desc, String contact, float acreage, float price, int idUser) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.image = image;
        this.desc = desc;
        this.contact = contact;
        this.acreage = acreage;
        this.price = price;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public float getAcreage() {
        return acreage;
    }

    public void setAcreage(float acreage) {
        this.acreage = acreage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
