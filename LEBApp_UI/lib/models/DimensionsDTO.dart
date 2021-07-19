import 'dart:collection';
import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable(explicitToJson: true)
class DimensionsDTO {

   double _height;
   double _width;
   double _depth;
   double _weight;

   DimensionsDTO(this._height,this._width,this._depth,this._weight);

   double get depth => _depth;

  double get width => _width;

  double get height => _height;

   set depth(double value) {
    _depth = value;
  }

  set width(double value) {
    _width = value;
  }

  set height(double value) {
    _height = value;
  }

   double get weight => _weight;

  set weight(double value) {
    _weight = value;
  }

  DimensionsDTO.fromJson(Map<String, dynamic> json)
       : _height = json['height'],
         _width = json['width'],
         _depth = json['depth'],
         _weight = json['weight'];

   Map<String, dynamic> toJson() => {
     'height' : _height,
     'width' : _width,
     'depth' :  _depth,
     'weight' :  _weight,
   };

   @override
  String toString() {
    return 'DimensionsDTO{_height: $_height, _width: $_width, _depth: $_depth, _weight: $_weight}';
  }
}