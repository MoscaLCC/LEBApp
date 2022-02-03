import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class UserInfo{

   int _id;
   String _firstName;
   String _lastName;
   String _email;
   String _imageUrl;
   String _phoneNumber;
   String _nib;
   int _nif;
   String _birthday;
   String _address;
   String _linkSocial;
   int _numberRequests;
   int _numberOfDeliveries;
   double _numberOfKm;
   double _payedValue;
   double _availableBalance;
   double _frozenBalance;
   double _ranking;

  UserInfo(
      this._firstName,
      this._lastName,
      this._email,
      this._imageUrl,
      this._phoneNumber,
      this._nib,
      this._nif,
      this._birthday,
      this._address,
      this._linkSocial,
      this._numberRequests,
      this._numberOfDeliveries,
      this._numberOfKm,
      this._payedValue,
      this._availableBalance,
      this._frozenBalance,
      this._ranking
      );

  String setToString(Set set) => IterableBase.iterableToFullString(set, '[', ']');

  UserInfo.fromJson(Map<String, dynamic> json)
      : _firstName = json['firstName'],
        _lastName = json['lastName'],
        _email = json['email'],
        _imageUrl = json['imageUrl'],
        _phoneNumber = json['phoneNumber'],
        _nib = json['nib'],
        _nif = json['nif'],
        _birthday = json['birthday'],
        _address = json['address'],
        _linkSocial = json['linkSocial'],
        _numberRequests = json['numberRequests'],
        _numberOfDeliveries = json['numberOfDeliveries'],
        _numberOfKm = json['numberOfKm'],
        _payedValue = json['payedValue'],
        _availableBalance = json['availableBalance'],
        _frozenBalance = json['frozenBalance'],
        _ranking = json['ranking'];

   UserInfo.toDefault()
       : _firstName = "",
         _lastName = "",
         _email = "",
         _imageUrl = "",
         _phoneNumber = "",
         _nib = "",
         _nif = 0,
         _birthday = "",
         _address = "",
         _linkSocial = "",
         _numberRequests = 0,
         _numberOfDeliveries = 0,
         _numberOfKm = 0.0,
         _payedValue = 0.0,
         _availableBalance = 0.0,
         _frozenBalance = 0.0,
         _ranking = 0.0;

  Map<String, dynamic> toJson() => {
    'firstName' :  _firstName,
     'lastName' :  _lastName,
     'email' :  _email,
     'imageUrl' :  _imageUrl,
     'phoneNumber' :  _phoneNumber,
     'nib' :  _nib,
     'nif' :  _nif,
     'birthday' :  _birthday,
     'address' :  _address,
     'linkSocial' :  _linkSocial,
     'numberRequests' :  _numberRequests,
     'numberOfDeliveries' :  _numberOfDeliveries,
     'numberOfKm' :  _numberOfKm,
     'payedValue' :  _payedValue,
     'availableBalance' :  _availableBalance,
     'frozenBalance' :  _frozenBalance,
     'ranking' :  _ranking,
  };

   double get ranking => _ranking;

  set ranking(double value) {
    _ranking = value;
  }

  double get frozenBalance => _frozenBalance;

  set frozenBalance(double value) {
    _frozenBalance = value;
  }

  double get availableBalance => _availableBalance;

  set availableBalance(double value) {
    _availableBalance = value;
  }

  double get payedValue => _payedValue;

  set payedValue(double value) {
    _payedValue = value;
  }

  double get numberOfKm => _numberOfKm;

  set numberOfKm(double value) {
    _numberOfKm = value;
  }

  int get numberOfDeliveries => _numberOfDeliveries;

  set numberOfDeliveries(int value) {
    _numberOfDeliveries = value;
  }

  int get numberRequests => _numberRequests;

  set numberRequests(int value) {
    _numberRequests = value;
  }

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

  String get nib => _nib;

  set nib(String value) {
    _nib = value;
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

  int get id => _id;

  set id(int value) {
    _id = value;
  }

   @override
  String toString() {
    return 'UserInfo{_id: $_id, _firstName: $_firstName, _lastName: $_lastName, _email: $_email, _imageUrl: $_imageUrl, _phoneNumber: $_phoneNumber, _nib: $_nib, _nif: $_nif, _birthday: $_birthday, _address: $_address, _linkSocial: $_linkSocial, _numberRequests: $_numberRequests, _numberOfDeliveries: $_numberOfDeliveries, _numberOfKm: $_numberOfKm, _payedValue: $_payedValue, _availableBalance: $_availableBalance, _frozenBalance: $_frozenBalance, _ranking: $_ranking}';
  }

}