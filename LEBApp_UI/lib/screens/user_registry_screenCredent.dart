import 'dart:convert';

import 'package:flutter/material.dart';
import 'dart:io';
import 'package:http/http.dart' as http;

import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import '../main.dart';

// ignore: camel_case_types
class User_Registry_ScreenCredent extends StatefulWidget {

  final User uTest;
  User_Registry_ScreenCredent({Key key, this.uTest}) : super(key: key);

  @override
  _User_Registry_ScreenCredentState createState() => _User_Registry_ScreenCredentState();
}

class _User_Registry_ScreenCredentState extends State<User_Registry_ScreenCredent> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  // Controllers
  final emailController = TextEditingController();
  final passwordController = TextEditingController();
  final confirmPasswordController = TextEditingController();


  @override
  Widget build(BuildContext context) {

    final emailField = TextField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Choose Your Email',icon: Icon(Icons.assignment_ind),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: emailController,
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

    final submitRegButon = Material(
      child: TextButton(
          child: Text('Submit'),
          style: TextButton.styleFrom(
            alignment: Alignment.bottomCenter,
            primary: Colors.white,
            backgroundColor: Colors.teal,
            onSurface: Colors.grey,
          ),
          onPressed: () {
            print(" ## Send object ## ");

            User uAux = new User(emailController.text, passwordController.text, widget.uTest.firstName, widget.uTest.lastName, emailController.text,
                'empty', true, 'empty', 'x', widget.uTest.createdDate, 'empty', widget.uTest.lastModifiedDate, widget.uTest.phoneNumber, 'empty', widget.uTest.nif,
                widget.uTest.birthday, widget.uTest.address, widget.uTest.isTransporter, widget.uTest.favouriteTransport, widget.uTest.isProducer,widget.uTest.linkSocial, widget.uTest.isPoint, 'empty', widget.uTest.isDeliveryMan, 'empty');

            var  url = Uri.parse('http://192.168.1.110:8080/api/register');
            var body = json.encode(uAux.toJson());
            print(body);

            Map<String,String> headers = {
              'Content-type' : 'application/json',
              'Accept': 'application/json',
            };

            final response = http.post(url, body: body, headers: headers);
            final responseJson = response;
            print(responseJson);
            return response;
          }
      ),
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
                    emailField,
                    SizedBox(height: 30.0),
                    passwordField,
                    SizedBox(height: 30.0),
                    confirmPasswordField,
                    SizedBox(height: 30.0),
                    submitRegButon,
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