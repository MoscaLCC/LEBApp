import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:lebapp_ui/models/User.dart';
import 'package:lebapp_ui/screens/user_registryProfile_screen.dart';
import 'package:lebapp_ui/widgets/UserForm.dart';
import '../main.dart';
import 'package:intl/intl.dart';

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
      decoration: InputDecoration(hintText: 'Your first name',icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: firstNameController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a name';
        return null;
      },
    );

    final lastNameField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your last name',icon: Icon(Icons.people),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: lastNameController,
      validator: (value) {
        if (value.isEmpty) return 'You have to insert a name';
        return null;
      },
    );

    final birthdayField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your birthay date',icon: Icon(Icons.calendar_today),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: birthdayController,
      onTap: () async{
        DateTime date = DateTime(1900);
        FocusScope.of(context).requestFocus(new FocusNode());

        date = await showDatePicker(
            context: context,
            initialDate:DateTime.now(),
            firstDate:DateTime(1900),
            lastDate: DateTime(2100));

        birthdayController.text = DateFormat("yyyy-MM-dd").format(date);},
    );

    final phoneNumberField = TextFormField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Your phone number',icon: Icon(Icons.phone_android),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
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
      decoration: InputDecoration(hintText: 'Your NIF',icon: Icon(Icons.info),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
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
      decoration: InputDecoration(hintText: 'Your address',icon: Icon(Icons.home),border: OutlineInputBorder(borderRadius: BorderRadius.circular(30.0)),contentPadding: EdgeInsets.fromLTRB(20.0,10.0,20.0,10.0)),
      controller: addressController,
        validator: (value) {
          if (value.isEmpty) return 'You have to insert a address';
          return null;
        }
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

          _saveForm(); // validar campos

          // Object generation
          User uAux = new User(firstNameController.text, lastNameController.text, null,
              null, phoneNumberController.text, int.parse(nifController.text), DateTime.parse(birthdayController.text), addressController.text,
              false, null,false,null,false, null,null,false, null,null,null);

          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>User_RegistryProfile_Screen(uTest: uAux)));
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
                    phoneNumberField,
                    SizedBox(height: 30.0),
                    nifField,
                    SizedBox(height: 30.0),
                    addressField,
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