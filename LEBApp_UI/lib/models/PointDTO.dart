import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class PointDTO {

  int _id;
  String _name;
  String _openingTime;
  String _closingTime;
  String _address;
  int _numberOfDeliveries;
  int _status;
  int _ownerPoint;

  PointDTO(this._id,this._name,this._openingTime,this._closingTime,this._address,this._numberOfDeliveries,this._ownerPoint);

  int get ownerPoint => _ownerPoint;

  set ownerPoint(int value) {
    _ownerPoint = value;
  }

  int get numberOfDeliveries => _numberOfDeliveries;

  set numberOfDeliveries(int value) {
    _numberOfDeliveries = value;
  }

  String get address => _address;

  set address(String value) {
    _address = value;
  }

  String get closingTime => _closingTime;

  set closingTime(String value) {
    _closingTime = value;
  }

  String get openingTime => _openingTime;

  set openingTime(String value) {
    _openingTime = value;
  }

  int get id => _id;

  set id(int value) {
    _id = value;
  }

  int get status => _status;

  set status(int value) {
    _status = value;
  }

  String get name => _name;

  set name(String value) {
    _name = value;
  }

  PointDTO.fromJson(Map<String, dynamic> json)
      : _id = json['id'],
        _name= json['name'],
       _openingTime = json['openingTime'],
       _closingTime = json['closingTime'],
       _address = json['address'],
       _numberOfDeliveries = json['numberOfDeliveries'],
       _status = json['status'],
       _ownerPoint = json['ownerPoint'];

  Map<String, dynamic> toJson() => {
    'id' : _id,
    'name' : _name,
    'openingTime' : _openingTime,
    'closingTime' : _closingTime,
    'address' : _address,
    'numberOfDeliveries' : _numberOfDeliveries,
    'status' : _status,
    'ownerPoint' : _ownerPoint};

  @override
  String toString() {
    return 'PointDTO{_id: $_id, _name: $_name, _openingTime: $_openingTime, _closingTime: $_closingTime, _address: $_address, _numberOfDeliveries: $_numberOfDeliveries, _status: $_status, _ownerPoint: $_ownerPoint}';
  }
}

