import 'dart:io';

import 'package:flutter/material.dart';
import '../main.dart';
import 'package:lebapp_ui/screens/user_registry_screenCredent.dart';
// ignore: camel_case_types
class User_RegistryProfile_Transporter extends StatefulWidget {

  @override
  _User_RegistryProfile_TransporterState createState() => _User_RegistryProfile_TransporterState();
}

class _User_RegistryProfile_TransporterState extends State<User_RegistryProfile_Transporter> {
  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  final favouriteTransportController = TextEditingController();

  @override
  Widget build(BuildContext context) {

    final favouriteTransport = TextField(
      obscureText: false,
      style: style,
      decoration: InputDecoration(hintText: 'Favourite Transport'),
      controller: favouriteTransportController,
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
          title: Text('Transporter Info'),
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
                    favouriteTransport,
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