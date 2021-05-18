import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class User{
   String _login;
   String _password;
   String _firstName;
   String _lastName;
   String _email;
   String _imageUrl;
   bool _activated = false;
   String _langKey;
   String _createdBy;
   DateTime _createdDate;
   String _lastModifiedBy;
   DateTime _lastModifiedDate;
   String _phoneNumber;
   String _nib;
   int _nif;
   DateTime _birthday;
   String _address;
   bool _isTransporter;
   String _favouriteTransport;
   bool _isProducer;
   String _linkSocial;
   bool _isPoint;
   String _openingTimePoint;
   bool _isDeliveryMan;
   String _openingTime;
   Set<String> _authorities;

   String get login => _login;

  set login(String value) {
    _login = value;
  }

   String get password => _password;

   Set<String> get authorities => _authorities;

  set authorities(Set<String> value) {
    _authorities = value;
  }

  String get openingTime => _openingTime;

  set openingTime(String value) {
    _openingTime = value;
  }

  bool get isDeliveryMan => _isDeliveryMan;

  set isDeliveryMan(bool value) {
    _isDeliveryMan = value;
  }

  String get openingTimePoint => _openingTimePoint;

  set openingTimePoint(String value) {
    _openingTimePoint = value;
  }

  bool get isPoint => _isPoint;

  set isPoint(bool value) {
    _isPoint = value;
  }

  String get linkSocial => _linkSocial;

  set linkSocial(String value) {
    _linkSocial = value;
  }

  bool get isProducer => _isProducer;

  set isProducer(bool value) {
    _isProducer = value;
  }

  String get favouriteTransport => _favouriteTransport;

  set favouriteTransport(String value) {
    _favouriteTransport = value;
  }

  bool get isTransporter => _isTransporter;

  set isTransporter(bool value) {
    _isTransporter = value;
  }

  String get address => _address;

  set address(String value) {
    _address = value;
  }

  DateTime get birthday => _birthday;

  set birthday(DateTime value) {
    _birthday = value;
  }

  int get nif => _nif;

  set nif(int value) {
    _nif = value;
  }

  String get nib => _nib;

  set nib(String value) {
    _nib = value;
  }

   String get phoneNumber => _phoneNumber;

  set phoneNumber(String value) {
    _phoneNumber = value;
  }

  DateTime get lastModifiedDate => _lastModifiedDate;

  set lastModifiedDate(DateTime value) {
    _lastModifiedDate = value;
  }

  String get lastModifiedBy => _lastModifiedBy;

  set lastModifiedBy(String value) {
    _lastModifiedBy = value;
  }

  DateTime get createdDate => _createdDate;

  set createdDate(DateTime value) {
    _createdDate = value;
  }

  String get createdBy => _createdBy;

  set createdBy(String value) {
    _createdBy = value;
  }

  String get langKey => _langKey;

  set langKey(String value) {
    _langKey = value;
  }

  bool get activated => _activated;

  set activated(bool value) {
    _activated = value;
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

   User(
      this._login,
      this._password,
      this._firstName,
      this._lastName,
      this._email,
      this._imageUrl,
      this._activated,
      this._langKey,
      this._createdBy,
      this._createdDate,
      this._lastModifiedBy,
      this._lastModifiedDate,
      this._phoneNumber,
      this._nib,
      this._nif,
      this._birthday,
      this._address,
      this._isTransporter,
      this._favouriteTransport,
      this._isProducer,
      this._linkSocial,
      this._isPoint,
      this._openingTimePoint,
      this._isDeliveryMan,
      this._openingTime,
      this._authorities);


   //factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);

   //Map<String, dynamic> toJson() => _$UserToJson(this);

   String setToString(Set set) => IterableBase.iterableToFullString(set, '[', ']');

   User.fromJson(Map<String, dynamic> json)
       : _login = json['login'],
         _password = json['password'],
         _firstName = json['firstName'],
         _lastName = json['lastName'],
         _email = json['email'],
         _imageUrl = json['imageUrl'],
         _activated = json['activated'],
         _langKey = json['langKey'],
         _createdBy = json['createdBy'],
         _createdDate = json['createdDate'],
         _lastModifiedBy = json['lastModifiedBy'],
         _lastModifiedDate = json['lastModifiedDate'],
         _phoneNumber = json['phoneNumber'],
         _nib = json['nib'],
         _nif = json['nif'],
         _birthday = json['_birthday'],
         _address = json['_address'],
         _isTransporter = json['isTransporter'],
         _favouriteTransport = json['favouriteTransport'],
         _isProducer = json['isProducer'],
         _linkSocial = json['linkSocial'],
         _isPoint = json['isPoint'],
         _openingTimePoint = json['openingTimePoint'],
         _isDeliveryMan = json['isDeliveryMan'],
         _openingTime = json['openingTime'],
         _authorities = json['authorities'];


   Map<String, dynamic> toJson() => {
   'login' : _login,
   'password' : _password,
   'firstName' :  _firstName,
   'lastName': _lastName,
   'email' : _email,
   'imageUrl' : _imageUrl,
   'activated' : _activated,
   'langKey' : _langKey,
   'createdBy' : _createdBy,
   'createdDate' : _createdDate,
   'lastModifiedBy' :  _lastModifiedBy,
   'lastModifiedDate' : _lastModifiedDate,
   'phoneNumber' :  _phoneNumber,
   'nib' : _nib,
   'nif' : _nif,
   '_birthday' : _birthday,
   '_address' : _address,
   'isTransporter' : _isTransporter,
   'favouriteTransport' : _favouriteTransport,
   'isProducer' : _isProducer,
   'linkSocial' : _linkSocial,
   'isPoint' : _isPoint,
   'openingTimePoint': _openingTimePoint,
   'isDeliveryMan' : _isDeliveryMan,
   'openingTime' : _openingTime,
   'authorities' : setToString(_authorities),
   };
}