import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';
import 'DimensionsDTO.dart';
import 'RidePathDTO.dart';

@JsonSerializable(explicitToJson: true)
class CreateRequestDTO {

  double _productValue; //ok
  String _productName; //ok
  String _source; //ok
  String _destination; //ok
  String _destinationContact;
  String _initDate;
  String _expirationDate;
  String _description; //ok
  String _specialCharacteristics; //ok
  double _productWeight;
  String _status;
  String _estimatedDate;
  String _deliveryTime;
  double _shippingCosts;
  double _rating;
  DimensionsDTO _dimensions;
  RidePathDTO _ridePath;
  int _producer;

  CreateRequestDTO(
      this._productValue,
      this._productName,
      this._source,
      this._destination,
      this._destinationContact,
      this._initDate,
      this._expirationDate,
      this._description,
      this._specialCharacteristics,
      this._productWeight,
      this._status,
      this._estimatedDate,
      this._deliveryTime,
      this._shippingCosts,
      this._rating,
      this._dimensions,
      this._ridePath,
      this._producer);

  int get producer => _producer;

  set producer(int value) {
    _producer = value;
  }

  RidePathDTO get ridePath => _ridePath;

  set ridePath(RidePathDTO value) {
    _ridePath = value;
  }

  DimensionsDTO get dimensions => _dimensions;

  set dimensions(DimensionsDTO value) {
    _dimensions = value;
  }

  double get rating => _rating;

  set rating(double value) {
    _rating = value;
  }

  double get shippingCosts => _shippingCosts;

  set shippingCosts(double value) {
    _shippingCosts = value;
  }

  String get deliveryTime => _deliveryTime;

  set deliveryTime(String value) {
    _deliveryTime = value;
  }

  String get estimatedDate => _estimatedDate;

  set estimatedDate(String value) {
    _estimatedDate = value;
  }

  String get status => _status;

  set status(String value) {
    _status = value;
  }

  double get productWeight => _productWeight;

  set productWeight(double value) {
    _productWeight = value;
  }

  String get specialCharacteristics => _specialCharacteristics;

  set specialCharacteristics(String value) {
    _specialCharacteristics = value;
  }

  String get description => _description;

  set description(String value) {
    _description = value;
  }

  String get expirationDate => _expirationDate;

  set expirationDate(String value) {
    _expirationDate = value;
  }

  String get initDate => _initDate;

  set initDate(String value) {
    _initDate = value;
  }

  String get destinationContact => _destinationContact;

  set destinationContact(String value) {
    _destinationContact = value;
  }

  String get destination => _destination;

  set destination(String value) {
    _destination = value;
  }

  String get source => _source;

  set source(String value) {
    _source = value;
  }

  String get productName => _productName;

  set productName(String value) {
    _productName = value;
  }

  double get productValue => _productValue;

  set productValue(double value) {
    _productValue = value;
  }

  CreateRequestDTO.fromJson(Map<String, dynamic> json)
      : _productValue = json['productValue'],
        _productName = json['productName'],
        _source = json['source'],
        _destination = json['destination'],
        _destinationContact = json['destinationContact'],
        _initDate = json['initDate'],
        _expirationDate = json['expirationDate'],
        _description = json['description'],
        _specialCharacteristics = json['specialCharacteristics'],
        _productWeight = json['productWeight'],
        _status = json['status'],
        _estimatedDate = json['estimatedDate'],
        _deliveryTime = json['deliveryTime'],
        _shippingCosts = json['shippingCosts'],
        _rating = json['rating'],
        _dimensions = json['dimensions'],
        _ridePath = json['ridePath'],
        _producer = json['producer'];

  Map<String, dynamic> toJson() => {
    'productValue' : _productValue,
    'productName' : _productName,
    'source' :  _source,
    'destination' :  _destination,
    'destinationContact' :  _destinationContact,
    'initDate' :  _initDate,
    'expirationDate' :  _expirationDate,
    'description' :  _description,
    'specialCharacteristics' :  _specialCharacteristics,
    'productWeight' :  _productWeight,
    'status' :  _status,
    'estimatedDate' :  _estimatedDate,
    'deliveryTime' :  _deliveryTime,
    'shippingCosts' :  _shippingCosts,
    'rating' :  _rating,
    'dimensions' : _dimensions,
    'ridePath' :  _ridePath,
    'producer' :  _producer,
  };

  @override
  String toString() {
    return 'CreateRequestDTO{_productValue: $_productValue, _productName: $_productName, _source: $_source, _destination: $_destination, _destinationContact: $_destinationContact, _initDate: $_initDate, _expirationDate: $_expirationDate, _description: $_description, _specialCharacteristics: $_specialCharacteristics, _productWeight: $_productWeight, _status: $_status, _estimatedDate: $_estimatedDate, _deliveryTime: $_deliveryTime, _shippingCosts: $_shippingCosts, _rating: $_rating, _dimensions: $_dimensions, _ridePath: $_ridePath, _producerId: $_producer}';
  }
}