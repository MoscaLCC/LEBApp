import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import '../main.dart';

// ignore: camel_case_types
class User_Registry_Screen extends StatefulWidget {

  @override
  _User_Registry_ScreenState createState() => _User_Registry_ScreenState();
}

class _User_Registry_ScreenState extends State<User_Registry_Screen> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);


  final okRegButon = Material(
    child: TextButton(
      child: Text('Register!'),
      style: TextButton.styleFrom(
        alignment: Alignment.bottomCenter,
        primary: Colors.white,
        backgroundColor: Colors.teal,
        onSurface: Colors.grey,
      ),
      onPressed: () async {
        print("Link to server to Register...");

        User userTeste = new User('manel', 'vita1234', 'Joaquim', 'Alberto', 'berto@gmail.com', 'cenas', true, 'langKey', 'x', DateTime.now(), 'test', DateTime.now(), '+35195448796', '11121211', 24920594, DateTime.now(), 'rua x', true, 'car', false, 'ddd', false, null, false, null,{'ROLE_ADMIN'});

        var  url = Uri.parse('http://192.168.1.110:8080/api/register');
        var body = userTeste.toJson();

        Map<String,String> headers = {
          'Content-type' : 'application/json',
          'Accept': 'application/json',
        };


       final response = await http.post(url, body: body, headers: headers);
       final responseJson = json.decode(response.body);
       print(responseJson);
       return response;

      },
    ),
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          title: Text('Registration Page'),
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
                    okRegButon,
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

/*
class User_Registry_Screen extends StatefulWidget {

  @override
  _User_Registry_ScreenState createState() => _User_Registry_ScreenState();
}

class _User_Registry_ScreenState extends State<User_Registry_Screen> {

  final _keyForm = GlobalKey<FormState>(); // Our created key
  final nameController = TextEditingController();
  final passwordController = TextEditingController();
  final emailController = TextEditingController();

  void _saveForm(){

    if(_keyForm.currentState.validate()){
      _keyForm.currentState.save();
      print(nameController);
      print(emailController);
    }
  }

  @override
  Widget build(BuildContext context) {
    // The form widget
    return Form(
      key: _keyForm,
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: ListView(
          children: [
            Container(
              padding: const EdgeInsets.symmetric(vertical: 20),
              child: Text('Welcome to LEB - User Registry'),
            ),
            TextFormField(
              controller: nameController,
              decoration: InputDecoration(hintText: 'Insert your name.'),
              validator: (value) {
                if (value.isEmpty) return 'You have to insert a name';

                return null;
              },
            ),
            TextFormField(
              controller: passwordController,
              obscureText: true,
              decoration: InputDecoration(hintText: 'The password to log in.'),
              validator: (value) {
                if (value.length < 7)
                  return 'Password must have at least 6 chars.';

                return null;
              },
            ),
            TextFormField(
              controller: emailController,
              decoration:
              InputDecoration(hintText: 'E-mail to use for log in.'),
              validator: (value) {
                if (!value.contains('@gmail.com'))
                  return 'Only gmail emails allowed.';

                return null;
              },
            ),
            RaisedButton(
              child: Text('Apply'),
              onPressed: () {
                _saveForm();
              },
            )
          ],
        ),
      ),
    );
  }
}
  */