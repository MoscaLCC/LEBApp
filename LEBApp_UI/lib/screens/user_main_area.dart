import 'dart:io';

import 'package:flutter/material.dart';
import '../main.dart';

// ignore: camel_case_types
class User_Main_Area extends StatefulWidget {
  String s;
  User_Main_Area(this.s);

  @override
  _User_Main_AreaState createState() => _User_Main_AreaState(s);
}

class _User_Main_AreaState extends State<User_Main_Area> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);
  String s;
  _User_Main_AreaState(this.s);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
            title: Text('Hello '+s+' ! '),
            backgroundColor: Colors.teal),
    );
  }
}