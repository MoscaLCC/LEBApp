import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class User{

   String _firstName; //ok
   String _lastName; //ok
   String _email; // ok
   String _imageUrl; // ok
   String _phoneNumber; // ok
   int _nif; // ok
   String _nib; // ok
   String _birthday; //ok
   String _address; //ok
   String _linkSocial; //ok
   String _password; // ok
   String _langKey;

   String get password => _password;

  String get linkSocial => _linkSocial;

  set linkSocial(String value) {
    _linkSocial = value;
  }

  String get address => _address;

  set address(String value) {
    _address = value;
  }

   String get birthday => _birthday;

  set birthday(String value) {
    _birthday = value;
  }

  int get nif => _nif;

  set nif(int value) {
    _nif = value;
  }

   String get phoneNumber => _phoneNumber;

  set phoneNumber(String value) {
    _phoneNumber = value;
  }

  String get imageUrl => _imageUrl;

  set imageUrl(String value) {
    _imageUrl = value;
  }

  String get email => _email;

  set email(String value) {
    _email = value;
  }

  String get lastName => _lastName;

  set lastName(String value) {
    _lastName = value;
  }

  String get firstName => _firstName;

  set firstName(String value) {
    _firstName = value;
  }

  set password(String value) {
    _password = value;
  }

   String get langKey => _langKey;

   set langKey(String value) {
     _langKey = value;
   }

   String get nib => _nib;

  set nib(String value) {
    _nib = value;
  }

  User(
      this._firstName,
      this._lastName,
      this._email,
      this._imageUrl,
      this._phoneNumber,
      this._nif,
      this._nib,
      this._birthday,
      this._address,
      this._linkSocial,
      this._password,
      this._langKey,
       );

   String setToString(Set set) => IterableBase.iterableToFullString(set, '[', ']');

   User.fromJson(Map<String, dynamic> json)
       : _firstName = json['firstName'],
         _lastName = json['lastName'],
         _email = json['email'],
         _imageUrl = json['imageUrl'],
         _phoneNumber = json['phoneNumber'],
         _nif = json['nif'],
         _nib = json['nib'],
         _birthday = json['birthday'],
         _address = json['address'],
         _linkSocial = json['linkSocial'],
         _password = json['password'],
         _langKey = json['langKey'];


   Map<String, dynamic> toJson() => {
   'firstName' :  _firstName,
   'lastName': _lastName,
   'email' : _email,
   'imageUrl' : _imageUrl,
   'phoneNumber' :  _phoneNumber,
   'nif' : _nif,
     'nib' : _nib,
   'birthday' : _birthday,
   'address' : _address,
   'linkSocial' : _linkSocial,
     'password' : _password,
     'langKey' : _langKey,
   };

   @override
  String toString() {
    return 'User{_firstName: $_firstName, _lastName: $_lastName, _email: $_email, _imageUrl: $_imageUrl, _phoneNumber: $_phoneNumber, _nif: $_nif, _nib: $_nib, _birthday: $_birthday, _address: $_address, _linkSocial: $_linkSocial, _password: $_password, _langKey: $_langKey}';
  }
}