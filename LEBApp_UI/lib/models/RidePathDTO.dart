import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class RidePathDTO {

   String _source;
   String _destination;
   String _distance;
   String _estimatedTime;

   RidePathDTO(this._source,this._destination,this._distance,this._estimatedTime);

   String get estimatedTime => _estimatedTime;

  set estimatedTime(String value) {
    _estimatedTime = value;
  }

  String get distance => _distance;

  set distance(String value) {
    _distance = value;
  }

  String get destination => _destination;

  set destination(String value) {
    _destination = value;
  }

  String get source => _source;

  set source(String value) {
    _source = value;
  }

   RidePathDTO.fromJson(Map<String, dynamic> json)
       : _source = json['source'],
         _destination = json['destination'],
         _distance = json['distance'],
         _estimatedTime = json['estimatedTime'] ;

   Map<String, dynamic> toJson() => {
     'source' : _source,
     'destination' : _destination,
     'distance' :  _distance,
     'estimatedTime' :  _estimatedTime,
   };

   @override
  String toString() {
    return 'RidePathDTO{_source: $_source, _destination: $_destination, _distance: $_distance, _estimatedTime: $_estimatedTime}';
  }
}