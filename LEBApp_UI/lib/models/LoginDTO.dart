import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class LoginDTO{
  String _token;
  String _firstName;
  String _lastName;
  List<dynamic> _profiles;

  LoginDTO(this._token, this._firstName, this._lastName, this._profiles);

  List<dynamic> get profiles => _profiles;

  String get lastName => _lastName;

  String get firstName => _firstName;

  String get token => _token;

  set profiles(List<dynamic> value) {
    _profiles = value;
  }

  set lastName(String value) {
    _lastName = value;
  }

  set firstName(String value) {
    _firstName = value;
  }

  set token(String value) {
    _token = value;
  }

  LoginDTO.fromJson(Map<String, dynamic> json)
      : _token = json['token'],
        _firstName = json['firstName'],
        _lastName = json['lastName'],
        _profiles = json['profiles'];

  Map<String, dynamic> toJson() => {
    'token' : _token,
  'firstName' :  _firstName,
  'lastName': _lastName,
  'profiles' : _profiles,
  };

  @override
  String toString() {
    return 'LoginDTO{_token: $_token, _firstName: $_firstName, _lastName: $_lastName, _profiles: $_profiles}';
  }
}