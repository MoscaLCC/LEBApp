import 'dart:developer';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import 'package:lebapp_ui/screens/user_main_area.dart';
import 'package:lebapp_ui/screens/user_registry_screen.dart';
import '../main.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:io';


class User_Reg_Screen extends StatefulWidget {
  @override
  _User_Reg_ScreenState createState() => _User_Reg_ScreenState();
}

class _User_Reg_ScreenState extends State<User_Reg_Screen> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  final controllerEmailTextField = TextEditingController();
  final controllerPasswordTextField = TextEditingController();

  @override
  Widget build(BuildContext context) {

    final emailField = TextField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Insert your username.'),
      controller: controllerEmailTextField,

    );

    final passwordField = TextField(
      obscureText: true,
      style: style,
      decoration: InputDecoration(hintText: 'Insert your password.'),
      controller: controllerPasswordTextField,
    );

    final loginButon = Material(
      child: TextButton(
        child: Text('Login'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () async {
          print("Link to server to...");

          var  url = Uri.parse('http://192.168.1.110:8080/api/authenticate');
          var body = json.encode({"username": "Admin", "password": "admin", "rememberMe": "false"});

          Map<String,String> headers = {
            'Content-type' : 'application/json',
            'Accept': 'application/json',
          };

          Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => User_Main_Area(controllerEmailTextField.text)));

          //final response = await http.post(url, body: body, headers: headers);
          //final responseJson = json.decode(response.body);
          //print(responseJson);
          //return response

        },
      ),
    );

    final backButon = Material(
      child: TextButton(
        child: Text('BACK BUTTON'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          //button action
          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>MyApp()));
        },
      ),
    );

    final regButon = Material(
      child: TextButton(
        child: Text('Not signed ? Register now'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          //button action
          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_Registry_Screen()));
        },
      ),
    );

    return Scaffold(
        appBar: AppBar(
            title: const Text('Login Screen'),
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
                    SizedBox(
                      height: 155.0,
                      child: Image.asset(
                        "assets/pp.jpg",
                        fit: BoxFit.contain,
                      ),
                    ),
                    SizedBox(height: 45.0),
                    emailField,
                    SizedBox(height: 25.0),
                    passwordField,
                    SizedBox(
                      height: 35.0,
                    ),
                    loginButon,
                    SizedBox(
                      height: 15.0,
                    ),
                    backButon,
                    SizedBox(
                      height: 15.0,
                    ),
                    regButon,
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