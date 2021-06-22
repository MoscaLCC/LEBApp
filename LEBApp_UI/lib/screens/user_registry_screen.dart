import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import 'package:lebapp_ui/screens/user_registryProfile_screen.dart';
import 'package:lebapp_ui/widgets/UserForm.dart';
import '../main.dart';

// ignore: camel_case_types
class User_Registry_Screen extends StatefulWidget {

  @override
  _User_Registry_ScreenState createState() => _User_Registry_ScreenState();
}

class _User_Registry_ScreenState extends State<User_Registry_Screen> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  // key to form validator
  final _keyForm = GlobalKey<FormState>(); // Our created key
  // Controllers
  final firstNameController = TextEditingController();
  final lastNameController = TextEditingController();
  final birthdayController = TextEditingController();
  final emailController = TextEditingController();
  final phoneNumberController = TextEditingController();
  final addressController = TextEditingController();
  final nifController = TextEditingController();

  void _saveForm(){
    if(_keyForm.currentState.validate()){
      _keyForm.currentState.save();
    }
  }

  @override
  Widget build(BuildContext context) {

    final firstNameField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your first name',border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: firstNameController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a name';
        return null;
      },
    );

    final lastNameField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your last name',border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: lastNameController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a name';
        return null;
      },
    );

    final birthdayField = TextField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your birthday date'),
      controller: birthdayController,
    );

    final emailField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your email',border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: emailController,
      validator: (value) {
        if (!value.contains('@gmail.com'))
          return 'Only gmail emails allowed.';
        return null;
      },
    );

    final phoneNumberField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your phone number',border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)), 
        keyboardType: TextInputType.number,
        controller: phoneNumberController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a number';
        return null;
      }
    );

    final nifField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your NIF',border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      keyboardType: TextInputType.number,
      controller: nifController,
        validator: (value) {
          if (value.isEmpty) return 'You have to insert a number';
          return null;
        }
    );

    final addressField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your address',border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: addressController,
        validator: (value) {
          if (value.isEmpty) return 'You have to insert a address';
          return null;
        }
    );

    final okRegButon = Material(
      child: TextButton(
        child: Text('Test Register !'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () async {

          _saveForm(); // validar campos

          print("Link to server to User Register...");

          /*
          User userTeste = new User('aurelio', 'mat9999', 'Matias', 'Belo', 'antonio.fmaio@gmail.com',
              'cenas', true, 'langKey', 'x', DateTime.now(), 'test', DateTime.now(), '+35195448796', '11121211', 24589665,
              DateTime.now(), 'rua x', true, 'car', false, 'ddd', false, null, false, null);

          var  url = Uri.parse('http://192.168.1.110:8080/api/register');
          var body = json.encode(userTeste.toJson());
          print(body);

          Map<String,String> headers = {
            'Content-type' : 'application/json',
            'Accept': 'application/json',
          };

          final response = await http.post(url, body: body, headers: headers);
          final responseJson = response;//json.decode(response.body);
          print(responseJson);
          return response;
*/
        },
      ),
    );

    final nextButon = Material(
      child: TextButton(
        child: Text('Go to Profile Info'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_RegistryProfile_Screen()));
        }
      ),
    );

    return Scaffold(
      appBar: AppBar(
          title: Text('Your basic info'),
          backgroundColor: Colors.teal),
        body: SingleChildScrollView(
          child: Form(
            key: _keyForm,
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
                    firstNameField,
                    SizedBox(height: 30.0),
                    lastNameField,
                    SizedBox(height: 30.0),
                    birthdayField,
                    SizedBox(height: 30.0),
                    emailField,
                    SizedBox(height: 30.0),
                    phoneNumberField,
                    SizedBox(height: 30.0),
                    nifField,
                    SizedBox(height: 30.0),
                    addressField,
                    okRegButon,
                    SizedBox(
                      height: 15.0,
                    ),
                    nextButon,
                    SizedBox(
                      height: 15.0,
                    ),
                  ],
                ),
              ),
            ),
          ),
        ),
        ),
    );
  }
}