import 'dart:io';

import 'package:flutter/material.dart';
import '../main.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';


class User_RegistryProfile_Point extends StatefulWidget {

  @override
  _User_RegistryProfile_PointState createState() => _User_RegistryProfile_PointState();
}

class _User_RegistryProfile_PointState extends State<User_RegistryProfile_Point> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  //final format = DateFormat("HH:mm");

  @override
  Widget build(BuildContext context) {

    return Scaffold(
        appBar: AppBar(
            title: Text('Point Info'),
            backgroundColor: Colors.teal),
        body: SingleChildScrollView(
          child: Center(
            child: Container(
              color: Colors.white,
              child: Padding(
                padding: const EdgeInsets.all(36.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    SizedBox(height: 30.0),
                    //linkSocial,
                  ],
                ),
              ),
            ),
          ),
        )
    );
  }
}