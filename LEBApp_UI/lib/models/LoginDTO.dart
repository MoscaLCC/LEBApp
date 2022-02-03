import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class LoginDTO{
  String _token;
  int _userId;
  String _firstName;
  String _lastName;

  LoginDTO(this._token, this._userId, this._firstName, this._lastName);

  String get lastName => _lastName;

  String get firstName => _firstName;

  String get token => _token;

  set lastName(String value) {
    _lastName = value;
  }

  set firstName(String value) {
    _firstName = value;
  }

  set token(String value) {
    _token = value;
  }

  int get userID => _userId;

  set userID(int value) {
    _userId = value;
  }

  LoginDTO.fromJson(Map<String, dynamic> json)
      : _token = json['token'],
        _userId = json['userId'],
        _firstName = json['firstName'],
        _lastName = json['lastName'];

  Map<String, dynamic> toJson() => {
    'token' : _token,
    'userId' : _userId,
  'firstName' :  _firstName,
  'lastName': _lastName,
  };

  @override
  String toString() {
    return 'LoginDTO{_token: $_token, _userId: $_userId, _firstName: $_firstName, _lastName: $_lastName}';
  }
}