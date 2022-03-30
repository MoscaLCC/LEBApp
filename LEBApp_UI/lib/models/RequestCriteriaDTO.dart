import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class RequestCriteriaDTO{

  int _ownerRequest;
  String _status;
  int _transporter;

  RequestCriteriaDTO(this._ownerRequest,this._status,this._transporter);

  int get transporter => _transporter;

  set transporter(int value) {
    _transporter = value;
  }

  int get ownerRequest => _ownerRequest;

  set ownerRequest(int value) {
    _ownerRequest = value;
  }

  String get status => _status;

  set status(String value) {
    _status = value;
  }

  String setToString(Set set) => IterableBase.iterableToFullString(set, '[', ']');

  RequestCriteriaDTO.fromJson(Map<String, dynamic> json)
      : _ownerRequest = json['ownerRequest'],
        _status = json['status'],
        _transporter = json['transporter'];


  Map<String, dynamic> toJson() => {
    'ownerRequest': _ownerRequest,
    'status' :  _status,
    'transporter' : _transporter,
  };

  @override
  String toString() {
    return 'RequestCriteriaDTO{_ownerRequest: $_ownerRequest, _status: $_status, _transporter: $_transporter}';
  }
}