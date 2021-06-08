import 'dart:io';

import 'package:flutter/material.dart';
import 'package:lebapp_ui/screens/user_registry_screenCredent.dart';
import '../main.dart';

// ignore: camel_case_types
class User_RegistryProfile_Producer extends StatefulWidget {

  @override
  _User_RegistryProfile_ProducerState createState() => _User_RegistryProfile_ProducerState();
}

class _User_RegistryProfile_ProducerState extends State<User_RegistryProfile_Producer> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  final linkSocialController = TextEditingController();

  @override
  Widget build(BuildContext context) {

    final linkSocial = TextField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Social Link'),
      controller: linkSocialController,
    );

    final nextButon = Material(
      child: TextButton(
          child: Text('Next'),
          style: TextButton.styleFrom(
            alignment: Alignment.bottomCenter,
            primary: Colors.white,
            backgroundColor: Colors.teal,
            onSurface: Colors.grey,
          ),
          onPressed: () {
            Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_Registry_ScreenCredent()));
          }
      ),
    );

    return Scaffold(
        appBar: AppBar(
            title: Text('Producer Info'),
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
                    linkSocial,
                    nextButon,
                    SizedBox(
                      height: 15.0,
                    ),
                  ],
                ),
              ),
            ),
          ),
        )
    );
  }
}