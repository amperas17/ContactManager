package com.vavan.contactmanager;

/**
 * Class help in working with database, it`s object has to strings: initial and translated
 */
public class DBRecord {

    private int id;
    private String image_path;
    private String description;
    private String phone_number;
    private Boolean is_favorite;


    public DBRecord(){

    }

    public DBRecord(String _image_path,String _description,String _phone_number,Boolean _is_favorite){
        image_path = _image_path;
        description = _description;
        phone_number = _phone_number;
        is_favorite = _is_favorite;

    }

    public DBRecord(int _id,String _image_path,String _description,String _phone_number,Boolean _is_favorite){
        id = _id;
        image_path = _image_path;
        description = _description;
        phone_number = _phone_number;
        is_favorite = _is_favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }


    public String getImagePath() {
        return image_path;
    }

    public void setImagePath(String _image_path) {
        image_path = _image_path;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        description = _description;
    }


    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String _phone_number) {
        phone_number = _phone_number;
    }


    public Boolean getIsFavorite() {
        return is_favorite;
    }

    public void setIsFavorite(Boolean _is_favorite) {
        is_favorite = _is_favorite;
    }





}
