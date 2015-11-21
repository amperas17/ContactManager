package com.vavan.contactmanager;

/**
 * Class help in working with database, it`s object has to strings: initial and translated
 */
public class DBRecord {

    private int id;
    private String description;
    private Boolean is_favorite;
    private String image_path;

    public DBRecord(){

    }

    public DBRecord(String _description,Boolean _is_favorite,String _image_path){
        description = _description;
        is_favorite = _is_favorite;
        image_path = _image_path;
    }

    public DBRecord(int _id,String _description,Boolean _is_favorite,String _image_path){
        id = _id;
        description = _description;
        is_favorite = _is_favorite;
        image_path = _image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        description = _description;
    }


    public Boolean getIsFavorite() {
        return is_favorite;
    }

    public void setIsFavorite(Boolean _is_favorite) {
        is_favorite = _is_favorite;
    }


    public String getImagePath() {
        return image_path;
    }

    public void setImagePath(String _image_path) {
        image_path = _image_path;
    }


}
