import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class User{
   String _firstName;
   String _lastName;
   String _email;
   String _imageUrl;
   String _phoneNumber;
   int _nif;
   DateTime _birthday;
   String _address;
   bool _isTransporter;
   String _favouriteTransport;
   bool _isProducer;
   String _linkSocial;
   bool _isPoint;
   String _openingTimePoint;
   String _closingTimePoint;
   bool _isDeliveryMan;
   String _openingTimeDeliveryMan;
   String _closingTimeDeliveryMan;
   String _password;

   String get password => _password;

  String get openingTimeDeliveryMan => _openingTimeDeliveryMan;

  set openingTimeDeliveryMan(String value) {
    _openingTimeDeliveryMan = value;
  }

   String get closingTimeDeliveryMan => _closingTimeDeliveryMan;

   set closingTimeDeliveryMan(String value) {
     _closingTimeDeliveryMan = value;
   }

   bool get isDeliveryMan => _isDeliveryMan;

  set isDeliveryMan(bool value) {
    _isDeliveryMan = value;
  }

  String get openingTimePoint => _openingTimePoint;

  set openingTimePoint(String value) {
    _openingTimePoint = value;
  }

   String get closingTimePoint => _closingTimePoint;

   set closingTimePoint(String value) {
     _closingTimePoint = value;
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

   User(
      this._firstName,
      this._lastName,
      this._email,
      this._imageUrl,
      this._phoneNumber,
      this._nif,
      this._birthday,
      this._address,
      this._isTransporter,
      this._favouriteTransport,
      this._isProducer,
      this._linkSocial,
      this._isPoint,
      this._openingTimePoint,
       this._closingTimePoint,
      this._isDeliveryMan,
      this._openingTimeDeliveryMan,
       this._closingTimeDeliveryMan,
       this._password
       );

   String setToString(Set set) => IterableBase.iterableToFullString(set, '[', ']');

   User.fromJson(Map<String, dynamic> json)
       : _firstName = json['firstName'],
         _lastName = json['lastName'],
         _email = json['email'],
         _imageUrl = json['imageUrl'],
         _phoneNumber = json['phoneNumber'],
         _nif = json['nif'],
         _birthday = json['birthday'],
         _address = json['address'],
         _isTransporter = json['isTransporter'],
         _favouriteTransport = json['favouriteTransport'],
         _isProducer = json['isProducer'],
         _linkSocial = json['linkSocial'],
         _isPoint = json['isPoint'],
         _openingTimePoint = json['openingTimePoint'],
         _closingTimePoint = json['closingTimePoint'],
         _isDeliveryMan = json['isDeliveryMan'],
         _openingTimeDeliveryMan = json['openingTimeDeliveryMan'],
         _closingTimeDeliveryMan = json['closingTimeDeliveryMan'],
    _password = json['password'];


   Map<String, dynamic> toJson() => {
   'firstName' :  _firstName,
   'lastName': _lastName,
   'email' : _email,
   'imageUrl' : _imageUrl,
   'phoneNumber' :  _phoneNumber,
   'nif' : _nif,
   'birthday' : _birthday.toString(),
   'address' : _address,
   'isTransporter' : _isTransporter.toString(),
   'favouriteTransport' : _favouriteTransport,
   'isProducer' : _isProducer.toString(),
   'linkSocial' : _linkSocial,
   'isPoint' : _isPoint.toString(),
   'openingTimePoint': _openingTimePoint,
     'closingTimePoint': _closingTimePoint,
   'isDeliveryMan' : _isDeliveryMan.toString(),
   'openingTimeDeliveryMan' : _openingTimeDeliveryMan,
     'closingTimeDeliveryMan' : _closingTimeDeliveryMan,
     'password' : _password,
   };

   @override
  String toString() {
    return 'User{_firstName: $_firstName, _lastName: $_lastName, _email: $_email, _imageUrl: $_imageUrl, _phoneNumber: $_phoneNumber, _nif: $_nif, _birthday: $_birthday, _address: $_address, _isTransporter: $_isTransporter, _favouriteTransport: $_favouriteTransport, _isProducer: $_isProducer, _linkSocial: $_linkSocial, _isPoint: $_isPoint, _openingTimePoint: $_openingTimePoint, _closingTimePoint: $_closingTimePoint, _isDeliveryMan: $_isDeliveryMan, _openingTimeDeliveryMan: $_openingTimeDeliveryMan, _closingTimeDeliveryMan: $_closingTimeDeliveryMan, _password: $_password}';
  }
}