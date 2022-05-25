package com.c323FinalProject.ctaddeuc;


public class User {

    private int _userId;
    private String _userEmail;
    private String _userName;
    private String _userImage;

    public User(){

    }

    public User(String _userEmail, String _userName, String _userImage){
        this._userEmail = _userEmail;
        this._userName = _userName;
        this._userImage = _userImage;
    }

    public int get_userId() {
        return _userId;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }

    public String get_userEmail() {
        return _userEmail;
    }

    public void set_userEmail(String _userEmail) {
        this._userEmail = _userEmail;
    }

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public String get_userImage() {
        return _userImage;
    }

    public void set_userImage(String _userImage) {
        this._userImage = _userImage;
    }
}
