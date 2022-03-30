import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class CreateRequestDTO {

  int _id;
  double _productValue;
  String _productName;
  String _source; // mapa
  String _destination; // mapa
  String _destinationContactMobile;
  String _destinationContactEmail;
  String _initDate;
  String _expirationDate;
  String _specialCharacteristics; // dropdown, para já compo texto
  double _weight;
  double _hight;
  double _width;
  String _status;
  double _shippingCosts;
  double _rating;
  int _ownerRequest;
  int _transporter;


  CreateRequestDTO(
      this._id,
      this._productValue,
      this._productName,
      this._source,
      this._destination,
      this._destinationContactMobile,
      this._destinationContactEmail,
      this._initDate,
      this._expirationDate,
      this._specialCharacteristics,
      this._weight,
      this._hight,
      this._width,
      this._status,
      this._shippingCosts,
      this._rating,
      this._ownerRequest,
      this._transporter);


  int get id => _id;

  set id(int value) {
    _id = value;
  }

  CreateRequestDTO.fromJson(Map<String, dynamic> json)
      : _id = json['id'],
        _productValue = json['productValue'],
        _productName = json['productName'],
        _source = json['source'],
        _destination = json['destination'],
        _destinationContactMobile = json['destinationContactMobile'],
        _destinationContactEmail = json['destinationContactEmail'],
        _initDate = json['initDate'],
        _expirationDate = json['expirationDate'],
        _specialCharacteristics = json['specialCharacteristics'],
        _weight = json['weight'],
        _hight = json['hight'],
        _width = json['width'],
        _status = json['status'].toString(),
        _shippingCosts = json['shippingCosts'],
        _rating = json['rating'],
        _ownerRequest = json['ownerRequest'],
        _transporter = json['transporter'];

  Map<String, dynamic> toJson() => {
    'id' : _id,
    'productValue' : _productValue,
    'productName' : _productName,
    'source' :  _source,
    'destination' :  _destination,
    'destinationContactMobile' :  _destinationContactMobile,
    'destinationContactEmail' :  _destinationContactEmail,
    'initDate' :  _initDate.toString(),
    'expirationDate' :  _expirationDate.toString(),
    'specialCharacteristics' :  _specialCharacteristics,
    'weight' : _weight,
    'hight' : _hight,
    'width' : _width,
    'status' :  _status,
    'shippingCosts' :  _shippingCosts,
    'rating' :  _rating,
    'ownerRequest' :  _ownerRequest,
    'transporter' :  _transporter,
  };

  @override
  String toString() {
    return 'CreateRequestDTO{_id: $_id, _productValue: $_productValue, _productName: $_productName, _source: $_source, _destination: $_destination, _destinationContactMobile: $_destinationContactMobile, _destinationContactEmail: $_destinationContactEmail, _initDate: $_initDate, _expirationDate: $_expirationDate, _specialCharacteristics: $_specialCharacteristics, _weight: $_weight, _hight: $_hight, _width: $_width, _status: $_status, _shippingCosts: $_shippingCosts, _rating: $_rating, _ownerRequest: $_ownerRequest, _transporter: $_transporter}';
  }

  double get productValue => _productValue;

  set productValue(double value) {
    _productValue = value;
  }

  String get productName => _productName;

  set productName(String value) {
    _productName = value;
  }

  String get source => _source;

  set source(String value) {
    _source = value;
  }

  String get destination => _destination;

  set destination(String value) {
    _destination = value;
  }

  String get destinationContactMobile => _destinationContactMobile;

  set destinationContactMobile(String value) {
    _destinationContactMobile = value;
  }

  String get destinationContactEmail => _destinationContactEmail;

  set destinationContactEmail(String value) {
    _destinationContactEmail = value;
  }

  String get initDate => _initDate;

  set initDate(String value) {
    _initDate = value;
  }

  String get expirationDate => _expirationDate;

  set expirationDate(String value) {
    _expirationDate = value;
  }

  String get specialCharacteristics => _specialCharacteristics;

  set specialCharacteristics(String value) {
    _specialCharacteristics = value;
  }

  double get weight => _weight;

  set weight(double value) {
    _weight = value;
  }

  double get hight => _hight;

  set hight(double value) {
    _hight = value;
  }

  double get width => _width;

  set width(double value) {
    _width = value;
  }

  String get status => _status;

  set status(String value) {
    _status = value;
  }

  double get shippingCosts => _shippingCosts;

  set shippingCosts(double value) {
    _shippingCosts = value;
  }

  double get rating => _rating;

  set rating(double value) {
    _rating = value;
  }

  int get ownerRequest => _ownerRequest;

  set ownerRequest(int value) {
    _ownerRequest = value;
  }

  int get transporter => _transporter;

  set transporter(int value) {
    _transporter = value;
  }
}