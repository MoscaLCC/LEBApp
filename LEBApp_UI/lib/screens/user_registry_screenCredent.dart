import 'dart:convert';

import 'package:flutter/material.dart';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import '../main.dart';

// ignore: camel_case_types
class User_Registry_ScreenCredent extends StatefulWidget {

  @override
  _User_Registry_ScreenCredentState createState() => _User_Registry_ScreenCredentState();
}

class _User_Registry_ScreenCredentState extends State<User_Registry_ScreenCredent> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  // Controllers
  final userNameController = TextEditingController();
  final passwordController = TextEditingController();
  final confirmPasswordController = TextEditingController();


  @override
  Widget build(BuildContext context) {

    final userNameField = TextField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Choose Your Username',icon: Icon(Icons.assignment_ind),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: userNameController,
    );

    final passwordField = TextField(
      obscureText: true,
      style: style,
      decoration: InputDecoration(hintText: 'Set Your Password',icon: Icon(Icons.vpn_key),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: passwordController,
    );

    final confirmPasswordField = TextField(
      obscureText: true,
      style: style,
      decoration: InputDecoration(hintText: 'Confirm Your Password',icon: Icon(Icons.vpn_key),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)), contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: confirmPasswordController,
    );


    return Scaffold(
        appBar: AppBar(
            title: Text('Your credentials info'),
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
                    userNameField,
                    SizedBox(height: 30.0),
                    passwordField,
                    SizedBox(height: 30.0),
                    confirmPasswordField,
                    SizedBox(height: 30.0),
                  ],
                ),
              ),
            ),
          ),
        )
    );
  }
}